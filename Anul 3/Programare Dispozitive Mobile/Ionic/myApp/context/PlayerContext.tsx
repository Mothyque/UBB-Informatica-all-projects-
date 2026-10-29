import React, { createContext, useContext, useEffect, useState, ReactNode, useCallback, useRef } from 'react';
import { normalizePlayer, Player, PlayerInput } from '../models/Player';
import {
  getPlayers,
  createPlayer,
  updatePlayer,
  deletePlayer,
  initWebSocket
} from '../services/playerApi';
import { getLocalPlayers, saveLocalPlayers } from '../services/storageService';
import { useAuth } from './AuthContext';
import { useNetworkStatus } from '../hooks/useNetworkStatus';

interface PlayerContextType {
  players: Player[];
  loading: boolean;
  refreshPlayers: () => Promise<void>;
  savePlayer: (playerData: PlayerInput, id?: string) => Promise<void>;
  removePlayer: (id: string) => Promise<void>;
  notification: string | null;
  clearNotification: () => void;
}

const PlayerContext = createContext<PlayerContextType>({
  players: [],
  loading: false,
  refreshPlayers: async () => {},
  savePlayer: async () => {},
  removePlayer: async () => {},
  notification: null,
  clearNotification: () => {},
});

export const PlayerProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const { user, token } = useAuth();
  const isOnline = useNetworkStatus();

  const isSyncingRef = useRef(false);
  const lastSyncTriggerRef = useRef<string | null>(null);

  const [players, setPlayers] = useState<Player[]>(() => getLocalPlayers(user?.id));
  const [loading, setLoading] = useState<boolean>(false);
  const [notification, setNotification] = useState<string | null>(null);

  const updatePlayersState = (newPlayers: Player[]) => {
    setPlayers(newPlayers);
    saveLocalPlayers(newPlayers, user?.id);
  };

  const syncPendingItems = useCallback(async () => {
    if (isSyncingRef.current) return;

    const currentList = getLocalPlayers(user?.id);
    const pendingItems = currentList.filter((p) => p.notSynced);
    if (pendingItems.length === 0) return;

    isSyncingRef.current = true;
    let updatedList = [...currentList];
    let failedItems = 0;

    try {
      for (const item of pendingItems) {
        try {
          const payload: PlayerInput = {
            name: item.name,
            team: item.team,
            pointsPerGame: Number(item.pointsPerGame) || 0,
            birthDate:
              item.birthDate instanceof Date
                ? item.birthDate
                : new Date(item.birthDate),
            isCaptain: Boolean(item.isCaptain),
            photo: item.photo || '',
            latitude: item.latitude !== undefined ? Number(item.latitude) : undefined,
            longitude: item.longitude !== undefined ? Number(item.longitude) : undefined,
          };

          if (item.id.startsWith('local_')) {
            const created = await createPlayer(payload);
            updatedList = updatedList.map((p) =>
              p.id === item.id ? { ...created, notSynced: false } : p
            );
          } else {
            const updated = await updatePlayer(item.id, payload);
            updatedList = updatedList.map((p) =>
              p.id === item.id ? { ...updated, notSynced: false } : p
            );
          }
        } catch (err) {
          failedItems += 1;
          console.error('Eșec la sincronizarea elementului:', item.name, err);
        }
      }

      updatePlayersState(updatedList);
      if (failedItems === 0) {
        setNotification('Elementele salvate offline au fost sincronizate cu serverul!');
      }
    } finally {
      isSyncingRef.current = false;
    }
  }, [user?.id]);

  const fetchList = useCallback(async () => {
    if (!token) return;

    if (navigator.onLine) {
      try {
        setLoading(true);
        const serverData = await getPlayers();
        const localOnly = getLocalPlayers(user?.id).filter((p) => p.notSynced);
        const merged = [
          ...localOnly,
          ...serverData.filter((s) => !localOnly.some((l) => l.id === s.id))
        ];
        updatePlayersState(merged);
      } catch (err) {
        console.warn('REST a eșuat, utilizăm datele din Local Storage');
        const local = getLocalPlayers(user?.id);
        setPlayers(local);
      } finally {
        setLoading(false);
      }
    } else {
      const local = getLocalPlayers(user?.id);
      setPlayers(local);
    }
  }, [token, user?.id]);

  useEffect(() => {
    const syncTrigger = `${token || 'anonymous'}:${isOnline}`;
    if (lastSyncTriggerRef.current === syncTrigger) return;

    lastSyncTriggerRef.current = syncTrigger;

    const loadAndSync = async () => {
      await fetchList();
      if (!token || !isOnline) return;

      await syncPendingItems();
      await fetchList();
    };

    void loadAndSync();

    if (!token) return;

    const cleanupWs = initWebSocket((msg) => {
      if (msg.event === 'player_created' && msg.payload) {
        const created = normalizePlayer(msg.payload);
        setPlayers((prev) => {
          const updated = [...prev.filter((p) => p.id !== created.id), created];
          saveLocalPlayers(updated, user?.id);
          return updated;
        });
        setNotification(`Jucător adăugat: ${created.name}`);
      } else if (msg.event === 'player_updated' && msg.payload) {
        const updatedItem = normalizePlayer(msg.payload);
        setPlayers((prev) => {
          const updated = prev.map((p) => (p.id === updatedItem.id ? updatedItem : p));
          saveLocalPlayers(updated, user?.id);
          return updated;
        });
        setNotification(`Jucător actualizat: ${updatedItem.name}`);
      } else if (msg.event === 'player_deleted' && msg.payload) {
        const deleted = msg.payload;
        setPlayers((prev) => {
          const updated = prev.filter((p) => p.id !== deleted.id);
          saveLocalPlayers(updated, user?.id);
          return updated;
        });
        setNotification(`Jucător șters: ${deleted.name}`);
      }
    });

    return () => cleanupWs();
  }, [fetchList, isOnline, syncPendingItems, token, user?.id]);

  const savePlayer = async (playerData: PlayerInput, id?: string) => {
    const isNew = !id || id === 'new';

    if (navigator.onLine) {
      try {
        if (isNew) {
          const created = await createPlayer(playerData);
          updatePlayersState([...players, created]);
        } else {
          const updated = await updatePlayer(id, playerData);
          updatePlayersState(players.map((p) => (p.id === id ? updated : p)));
        }
        return;
      } catch (err) {
        console.warn('Apelul REST a eșuat. Se salvează în regim Offline...');
      }
    }

    if (isNew) {
      const offlinePlayer: Player = {
        ...playerData,
        id: `local_${Date.now()}`,
        notSynced: true,
      };
      updatePlayersState([...players, offlinePlayer]);
      setNotification('Salvat local (Offline). Se va trimite la reconectare.');
    } else {
      const updatedList = players.map((p) =>
        p.id === id ? { ...p, ...playerData, notSynced: true } : p
      );
      updatePlayersState(updatedList);
      setNotification('Actualizat local (Offline). Se va trimite la reconectare.');
    }
  };

  const removePlayer = async (id: string) => {
    if (navigator.onLine) {
      try {
        await deletePlayer(id);
      } catch (err) {
        console.warn('Ștergerea pe server a eșuat, eliminăm local');
      }
    }
    updatePlayersState(players.filter((p) => p.id !== id));
  };

  return (
    <PlayerContext.Provider
      value={{
        players,
        loading,
        refreshPlayers: fetchList,
        savePlayer,
        removePlayer,
        notification,
        clearNotification: () => setNotification(null),
      }}
    >
      {children}
    </PlayerContext.Provider>
  );
};

export const usePlayers = () => useContext(PlayerContext);