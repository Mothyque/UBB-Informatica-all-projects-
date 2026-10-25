import { normalizePlayer, Player, PlayerResponse } from '../models/Player';

const getStorageKey = (userId?: string) => `euroleague_players_${userId || 'guest'}`;

export const getLocalPlayers = (userId?: string): Player[] => {
  try {
    const raw = localStorage.getItem(getStorageKey(userId));
    const storedPlayers = raw ? JSON.parse(raw) : [];
    return storedPlayers.map((player: PlayerResponse) => normalizePlayer(player));
  } catch (err) {
    console.error('Eroare la citirea din local storage:', err);
    return [];
  }
};

export const saveLocalPlayers = (players: Player[], userId?: string): void => {
  try {
    localStorage.setItem(getStorageKey(userId), JSON.stringify(players));
  } catch (err) {
    console.error('Eroare la salvarea în local storage:', err);
  }
};