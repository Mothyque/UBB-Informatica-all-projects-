import axios from 'axios';
import { Player, PlayerInput } from '../models/Player';

const BASE_URL = 'http://localhost:3000';
const API_URL = `${BASE_URL}/api/players`;
const WS_URL = 'ws://localhost:3000';

export const getPlayers = async (): Promise<Player[]> => {
  const response = await axios.get<Player[]>(API_URL);
  return response.data;
};

export const getPlayerById = async (id: string): Promise<Player> => {
  const response = await axios.get<Player>(`${API_URL}/${id}`);
  return response.data;
};

export const createPlayer = async (player: PlayerInput): Promise<Player> => {
  const response = await axios.post<Player>(API_URL, player);
  return response.data;
};

export const updatePlayer = async (id: string, player: Partial<PlayerInput>): Promise<Player> => {
  const response = await axios.put<Player>(`${API_URL}/${id}`, player);
  return response.data;
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
      const data: WsMessage = JSON.parse(event.data);
      onMessage(data);
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