import React, { createContext, useContext, useEffect, useState, ReactNode } from 'react';
import { Player } from '../models/Player';
import { getPlayers, initWebSocket } from '../services/playerApi';

interface PlayerContextType {
  players: Player[];
  loading: boolean;
  refreshPlayers: () => Promise<void>;
  notification: string | null;
  clearNotification: () => void;
}

const PlayerContext = createContext<PlayerContextType>({
  players: [],
  loading: false,
  refreshPlayers: async () => {},
  notification: null,
  clearNotification: () => {},
});

export const PlayerProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [players, setPlayers] = useState<Player[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [notification, setNotification] = useState<string | null>(null);

  const fetchList = async () => {
    try {
      setLoading(true);
      const data = await getPlayers();
      setPlayers(data);
    } catch (err) {
      console.error('Eroare la încărcarea jucătorilor:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchList();

    const cleanupWs = initWebSocket((msg) => {
      if (msg.event === 'player_created' && msg.payload) {
        const created = msg.payload;
        setPlayers((prev) => [...prev.filter((p) => p.id !== created.id), created]);
        setNotification(`Jucător adăugat: ${created.name} (${created.team})`);
      } else if (msg.event === 'player_updated' && msg.payload) {
        const updated = msg.payload;
        setPlayers((prev) => prev.map((p) => (p.id === updated.id ? updated : p)));
        setNotification(`Jucător actualizat: ${updated.name}`);
      } else if (msg.event === 'player_deleted' && msg.payload) {
        const deleted = msg.payload;
        setPlayers((prev) => prev.filter((p) => p.id !== deleted.id));
        setNotification(`Jucător șters: ${deleted.name}`);
      }
    });

    return () => cleanupWs();
  }, []);

  return (
    <PlayerContext.Provider
      value={{
        players,
        loading,
        refreshPlayers: fetchList,
        notification,
        clearNotification: () => setNotification(null),
      }}
    >
      {children}
    </PlayerContext.Provider>
  );
};

export const usePlayers = () => useContext(PlayerContext);