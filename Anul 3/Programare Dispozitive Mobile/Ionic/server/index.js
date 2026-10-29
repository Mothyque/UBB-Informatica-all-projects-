const express = require('express');
const http = require('http');
const WebSocket = require('ws');
const cors = require('cors');
const jwt = require('jsonwebtoken');
const fs = require('fs');
const path = require('path');

const app = express();
const port = 3000;
const JWT_SECRET = 'euroleague_secret_key'; 
const PLAYERS_FILE = path.join(__dirname, 'players.json');

app.use(cors());
app.use(express.json({limit: '10mb'}));

app.get('/', (req, res) => {
  res.json({
    status: 'online',
    message: 'Euroleague player API is running',
    api: '/api',
    websocket: 'ws://localhost:3000'
  });
});

const users = [
  { id: "u1", username: "coach1", password: "password123" },
  { id: "u2", username: "coach2", password: "password123" },
];

function loadPlayers() {
  try {
    const storedPlayers = JSON.parse(fs.readFileSync(PLAYERS_FILE, 'utf8'));
    if (!Array.isArray(storedPlayers)) {
      throw new Error('players.json must contain an array');
    }
    return storedPlayers.map((player) => ({
      ...player,
      birthDate: new Date(player.birthDate)
    }));
  } catch (err) {
    console.error(`Failed to load players from ${PLAYERS_FILE}:`, err);
    process.exit(1);
  }
}

function savePlayers() {
  const temporaryFile = `${PLAYERS_FILE}.tmp`;
  fs.writeFileSync(temporaryFile, JSON.stringify(players, null, 2));
  fs.renameSync(temporaryFile, PLAYERS_FILE);
}

let players = loadPlayers();

function authenticateToken(req, res, next) {
  const authHeader = req.headers['authorization'];
  const token = authHeader && authHeader.split(' ')[1];
  if (!token) return res.sendStatus(401).json({ error: 'Token missing' });

  jwt.verify(token, JWT_SECRET, (err, user) => {
    if (err) return res.sendStatus(403).json({ error: 'Invalid token' });
    req.user = user;
    next();
  });
}

app.post('/api/auth/login', (req, res) => {
  const { username, password } = req.body;
  const user = users.find(u => u.username === username && u.password === password);
  if (!user) return res.status(401).json({ error: 'Invalid credentials' });
  const token = jwt.sign({ id: user.id, username: user.username }, JWT_SECRET, { expiresIn: '1d' });
  res.json({ token, user: { id: user.id, username: user.username } });
});

function parseBoolean(value, fallback) {
  if (value === undefined) return fallback;
  if (typeof value === 'boolean') return value;
  if (typeof value === 'string') {
    if (value.toLowerCase() === 'true') return true;
    if (value.toLowerCase() === 'false') return false;
  }
  return Boolean(value);
}

function parseDate(value, fallback) {
  if (value === undefined || value === '') return fallback;
  const date = new Date(value);
  if (Number.isNaN(date.getTime())) return null;
  return date;
}

const server = http.createServer(app);
const wss = new WebSocket.Server({ noServer: true });

server.on('upgrade', (request, socket, head) => {
  const urlParams = new URLSearchParams(request.url.replace('/?', '?'));
  const token = urlParams.get('token');
  if (!token) {
    socket.write('HTTP/1.1 401 Unauthorized\r\n\r\n');
    socket.destroy();
    return;
  }

  jwt.verify(token, JWT_SECRET, (err, user) => {
    if (err) {
      socket.write('HTTP/1.1 403 Forbidden\r\n\r\n');
      socket.destroy();
      return;
    }

    wss.handleUpgrade(request, socket, head, (ws) => {
      ws.user = user;
      wss.emit('connection', ws, request);
    });
  });
});

function broadcastToUser(targetUserId, event, payload) {
  const message = JSON.stringify({ event, payload });
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN && client.user && client.user.id === targetUserId) {
      client.send(message);
    }
  });
}

wss.on('connection', (ws) => {
  console.log(`Client connected to WebSocket as user ${ws.user.username}`);
  ws.send(JSON.stringify({ event: 'connected', message: 'Conected as ' + ws.user.username }));

  ws.on('close', () => {
    console.log(`Client disconnected from WebSocket as user ${ws.user.username}`);
  });
});

app.get('/api/players', authenticateToken, (req, res) => {
  const userPlayers = players.filter(player => player.userId === req.user.id);
  res.json(userPlayers);
});

app.get('/api/players/:id', authenticateToken, (req, res) => {
  const player = players.find((p) => p.id === req.params.id && p.userId === req.user.id);
  if (!player) return res.status(404).json({ error: 'Player not found' });
  res.json(player);
});

app.post('/api/players', authenticateToken, (req, res) => {
  const { name, team, pointsPerGame, birthDate, isCaptain, photo, latitude, longitude } = req.body;
  const newPlayer = {
    id: Date.now().toString(),
    name: String(name || ''),
    team: String(team || ''),
    pointsPerGame: Number(pointsPerGame || 0),
    birthDate: parseDate(birthDate, new Date()),
    isCaptain: parseBoolean(isCaptain, false),
    photo: photo || '',
    latitude: latitude !== undefined ? Number(latitude) : undefined,
    longitude: longitude !== undefined ? Number(longitude) : undefined,
    userId: req.user.id
  };
  if (!newPlayer.birthDate) return res.status(400).json({ error: 'Invalid birth date' });
  players.push(newPlayer);
  savePlayers();
  broadcastToUser(req.user.id, 'player_created', newPlayer);
  res.status(201).json(newPlayer);
});

app.put('/api/players/:id', authenticateToken, (req, res) => {
  const index = players.findIndex((p) => p.id === req.params.id && p.userId === req.user.id);
  if (index === -1) return res.status(404).json({ error: 'Player not found' });

  const { name, team, pointsPerGame, birthDate, isCaptain, photo, latitude, longitude } = req.body;
  const updatedPlayer = {
    ...players[index],
    name: name !== undefined ? String(name) : players[index].name,
    team: team !== undefined ? String(team) : players[index].team,
    pointsPerGame: pointsPerGame !== undefined ? Number(pointsPerGame) : players[index].pointsPerGame,
    birthDate: parseDate(birthDate, players[index].birthDate),
    isCaptain: parseBoolean(isCaptain, players[index].isCaptain),
    photo: photo !== undefined ? String(photo) : players[index].photo,
    latitude: latitude !== undefined ? Number(latitude) : players[index].latitude,
    longitude: longitude !== undefined ? Number(longitude) : players[index].longitude
  };
  if (!updatedPlayer.birthDate) return res.status(400).json({ error: 'Invalid birth date' });
  players[index] = updatedPlayer;
  savePlayers();
  broadcastToUser(req.user.id, 'player_updated', updatedPlayer);
  res.json(updatedPlayer);
});

app.delete('/api/players/:id', authenticateToken, (req, res) => {
  const index = players.findIndex((p) => p.id === req.params.id && p.userId === req.user.id);
  if (index === -1) return res.status(404).json({ error: 'Player not found' });

  const deletedPlayer = players.splice(index, 1)[0];
  savePlayers();
  broadcastToUser(req.user.id, 'player_deleted', deletedPlayer);
  res.json({ message: 'Player removed', player: deletedPlayer });
});

server.listen(port, () => {
  console.log(`Server listening on http://localhost:${port}`);
  console.log(`WebSocket server listening on ws://localhost:${port}`);
});