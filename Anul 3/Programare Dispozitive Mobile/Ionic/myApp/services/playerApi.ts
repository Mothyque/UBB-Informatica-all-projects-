import axios from 'axios';
import { Player, PlayerInput } from '../models/Player';

const BASE_URL = 'http://localhost:3000';
const API_URL = `${BASE_URL}/api/players`;
const WS_URL = 'ws://localhost:3000';

interface PlayerResponse extends Omit<Player, 'birthDate'> {
  birthDate: string;
}

const fromApiPlayer = (player: PlayerResponse): Player => ({
  ...player,
  birthDate: new Date(player.birthDate),
});

const toApiPlayer = (player: PlayerInput | Partial<PlayerInput>) => ({
  ...player,
  ...(player.birthDate && { birthDate: player.birthDate.toISOString() }),
});

export const getPlayers = async (): Promise<Player[]> => {
  const response = await axios.get<PlayerResponse[]>(API_URL);
  return response.data.map(fromApiPlayer);
};

export const getPlayerById = async (id: string): Promise<Player> => {
  const response = await axios.get<PlayerResponse>(`${API_URL}/${id}`);
  return fromApiPlayer(response.data);
};

export const createPlayer = async (player: PlayerInput): Promise<Player> => {
  const response = await axios.post<PlayerResponse>(API_URL, toApiPlayer(player));
  return fromApiPlayer(response.data);
};

export const updatePlayer = async (id: string, player: Partial<PlayerInput>): Promise<Player> => {
  const response = await axios.put<PlayerResponse>(`${API_URL}/${id}`, toApiPlayer(player));
  return fromApiPlayer(response.data);
};

export const deletePlayer = async (id: string): Promise<void> => {
  await axios.delete(`${API_URL}/${id}`);
};

export interface WsMessage {
  event: 'player_created' | 'player_updated' | 'player_deleted' | 'connected';
  payload?: Player;
  message?: string;
}

export const initWebSocket = (onMessage: (msg: WsMessage) => void): (() => void) => {
  const ws = new WebSocket(WS_URL);

  ws.onopen = () => {
    console.log('[WebSocket] Conectat la serverul Euroleague');
  };

  ws.onmessage = (event) => {
    try {
      const data: WsMessage & { payload?: PlayerResponse } = JSON.parse(event.data);
      onMessage({
        ...data,
        payload: data.payload ? fromApiPlayer(data.payload) : undefined,
      });
    } catch (err) {
      console.error('[WebSocket] Eroare la parsarea mesajului:', err);
    }
  };

  ws.onerror = (err) => {
    console.error('[WebSocket] Eroare:', err);
  };

  ws.onclose = () => {
    console.log('[WebSocket] Deconectat de la server');
  };

  return () => {
    ws.close();
  };
};