const express = require('express');
const http = require('http');
const WebSocket = require('ws');
const cors = require('cors');

const app = express();
const port = 3000;

app.use(cors());
app.use(express.json());

let players = [
  {
    id: '1',
    name: 'Kostas Sloukas',
    team: 'Panathinaikos AKTOR',
    pointsPerGame: 12.7,
    birthDate: new Date('1990-01-15'),
    isCaptain: true
  },
  {
    id: '2',
    name: 'Facundo Campazzo',
    team: 'Real Madrid',
    pointsPerGame: 11.5,
    birthDate: new Date('1991-03-23'),
    isCaptain: false
  },
  {
    id: '3',
    name: 'Mike James',
    team: 'AS Monaco',
    pointsPerGame: 17.9,
    birthDate: new Date('1990-08-18'),
    isCaptain: true
  },
  {
    id: '4',
    name: 'Sasha Vezenkov',
    team: 'Olympiacos',
    pointsPerGame: 18.2,
    birthDate: new Date('1995-07-25'),
    isCaptain: false
  }
];

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
const wss = new WebSocket.Server({ server });

function broadcast(event, payload) {
  const message = JSON.stringify({ event, payload });
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(message);
    }
  });
}

wss.on('connection', (ws) => {
  console.log('Client connected to WebSocket');
  ws.send(JSON.stringify({ event: 'connected', message: 'WebSocket ready' }));

  ws.on('close', () => {
    console.log('Client disconnected');
  });
});

app.get('/', (req, res) => {
  res.json({
    message: 'Players API is running',
    playersEndpoint: '/api/players',
    websocketEndpoint: `ws://localhost:${port}`
  });
});

app.get('/api/players', (req, res) => {
  res.json(players);
});

app.get('/api/players/:id', (req, res) => {
  const player = players.find((p) => p.id === req.params.id);
  if (!player) return res.status(404).json({ error: 'Player not found' });
  res.json(player);
});

app.post('/api/players', (req, res) => {
  const { name, team, pointsPerGame, birthDate, isCaptain } = req.body;
  const newPlayer = {
    id: Date.now().toString(),
    name: String(name || ''),
    team: String(team || ''),
    pointsPerGame: Number(pointsPerGame || 0),
    birthDate: parseDate(birthDate, new Date()),
    isCaptain: parseBoolean(isCaptain, false)
  };
  if (!newPlayer.birthDate) return res.status(400).json({ error: 'Invalid birth date' });
  players.push(newPlayer);
  broadcast('player_created', newPlayer);
  res.status(201).json(newPlayer);
});

app.put('/api/players/:id', (req, res) => {
  const index = players.findIndex((p) => p.id === req.params.id);
  if (index === -1) return res.status(404).json({ error: 'Player not found' });

  const { name, team, pointsPerGame, birthDate, isCaptain } = req.body;
  const updatedPlayer = {
    ...players[index],
    name: name !== undefined ? String(name) : players[index].name,
    team: team !== undefined ? String(team) : players[index].team,
    pointsPerGame: pointsPerGame !== undefined ? Number(pointsPerGame) : players[index].pointsPerGame,
    birthDate: parseDate(birthDate, players[index].birthDate),
    isCaptain: parseBoolean(isCaptain, players[index].isCaptain)
  };
  if (!updatedPlayer.birthDate) return res.status(400).json({ error: 'Invalid birth date' });

  players[index] = updatedPlayer;
  broadcast('player_updated', updatedPlayer);
  res.json(updatedPlayer);
});

app.delete('/api/players/:id', (req, res) => {
  const index = players.findIndex((p) => p.id === req.params.id);
  if (index === -1) return res.status(404).json({ error: 'Player not found' });

  const deletedPlayer = players.splice(index, 1)[0];
  broadcast('player_deleted', deletedPlayer);
  res.json({ message: 'Player removed', player: deletedPlayer });
});

server.listen(port, () => {
  console.log(`Server listening on http://localhost:${port}`);
  console.log(`WebSocket server listening on ws://localhost:${port}`);
});