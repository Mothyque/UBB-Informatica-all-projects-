import axios from 'axios';
import { normalizePlayer, Player, PlayerInput, PlayerResponse } from '../models/Player';

const BASE_URL = 'http://localhost:3000';
const API_URL = `${BASE_URL}/api/players`;

axios.interceptors.request.use((config) => {
  const token = localStorage.getItem('euroleague_auth_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const getPlayers = async (): Promise<Player[]> => {
  const response = await axios.get<PlayerResponse[]>(API_URL);
  return response.data.map(normalizePlayer);
};

export const getPlayerById = async (id: string): Promise<Player> => {
  const response = await axios.get<PlayerResponse>(`${API_URL}/${id}`);
  return normalizePlayer(response.data);
};

export const createPlayer = async (player: PlayerInput): Promise<Player> => {
  const response = await axios.post<PlayerResponse>(API_URL, player);
  return normalizePlayer(response.data);
};

export const updatePlayer = async (id: string, player: Partial<PlayerInput>): Promise<Player> => {
  const response = await axios.put<PlayerResponse>(`${API_URL}/${id}`, player);
  return normalizePlayer(response.data);
};

export const deletePlayer = async (id: string): Promise<void> => {
  await axios.delete(`${API_URL}/${id}`);
};

export interface WsMessage {
  event: 'player_created' | 'player_updated' | 'player_deleted' | 'connected';
  payload?: PlayerResponse;
  message?: string;
}

export const initWebSocket = (onMessage: (msg: WsMessage) => void): (() => void) => {
  const token = localStorage.getItem('euroleague_auth_token');
  if (!token) return () => {};

  const ws = new WebSocket(`ws://localhost:3000?token=${token}`);

  ws.onopen = () => {
    console.log('[WebSocket Securizat] Conectat la server');
  };

  ws.onmessage = (event) => {
    try {
      const data: WsMessage = JSON.parse(event.data);
      onMessage(data);
    } catch (err) {
      console.error('[WebSocket] Eroare parsare:', err);
    }
  };

  ws.onclose = () => {
    console.log('[WebSocket] Conexiune închisă');
  };

  return () => {
    ws.close();
  };
};