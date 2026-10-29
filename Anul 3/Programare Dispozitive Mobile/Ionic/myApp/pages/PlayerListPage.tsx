import React, {useState, useMemo, useRef, useEffect } from 'react';
import {
  IonContent,
  IonHeader,
  IonPage,
  IonTitle,
  IonToolbar,
  IonList,
  IonItem,
  IonLabel,
  IonBadge,
  IonFab,
  IonFabButton,
  IonIcon,
  IonSpinner,
  IonToast,
  IonNote,
  IonButtons,
  IonButton,
  IonSearchbar,
  IonSelect,
  IonSelectOption,
  IonInfiniteScroll,
  IonInfiniteScrollContent,
  IonAvatar,
  createAnimation
} from '@ionic/react';
import { add, personCircleOutline, logOutOutline } from 'ionicons/icons';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { usePlayers } from '../context/PlayerContext';
import { useNetworkStatus } from '../hooks/useNetworkStatus';

const PAGE_SIZE = 5;

const PlayerListPage: React.FC = () => {
  const navigate = useNavigate();
  const { players, loading, notification, clearNotification } = usePlayers();
  const { logout, user } = useAuth();
  const isOnline = useNetworkStatus();

  const [searchText, setSearchText] = useState('');
  const [captainFilter, setCaptainFilter] = useState<string>('all');

  const [visibleCount, setVisibleCount] = useState<number>(PAGE_SIZE);

  const filteredPlayers = useMemo(() => {
    return players.filter((player) => {
      const matchesSearch = 
        player.name.toLowerCase().includes(searchText.toLowerCase()) || 
        player.team.toLowerCase().includes(searchText.toLowerCase());
      
      let matchesFilter = true;
      if (captainFilter === 'captains') {
        matchesFilter = player.isCaptain === true;
      } else if (captainFilter === 'non-captains') {
        matchesFilter = player.isCaptain === false;
      } else if (captainFilter === 'unsynced') {
        matchesFilter = player.notSynced === true;
      }

      return matchesSearch && matchesFilter;
    });
  }, [players, searchText, captainFilter]);

  const displayedPlayers = useMemo(() => {
    return filteredPlayers.slice(0, visibleCount);
  }, [filteredPlayers, visibleCount]);

  const loadMorePlayers = () => {
    setVisibleCount((prev) => prev + PAGE_SIZE);
  };

  const loadMoreData = (ev: CustomEvent<void>) => {
    setTimeout(() => {
      loadMorePlayers();
      (ev.target as HTMLIonInfiniteScrollElement).complete();
    }, 500);
  };

  const hasMorePlayers = visibleCount < filteredPlayers.length;

  const fabRef = useRef<HTMLIonFabElement>(null);

  useEffect(() => {
    if (fabRef.current) {
      const animation = createAnimation()
        .addElement(fabRef.current)
        .duration(1500)
        .iterations(Infinity)
        .keyframes([
          { offset: 0, transform: 'scale(1)'},
          { offset: 0.5, transform: 'scale(1.12)'},
          { offset: 1, transform: 'scale(1)'},
        ]);
      animation.play();

      return () => {
        animation.stop();
      };
    }
  }, []);

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar color="primary">
          <IonTitle>Euroleague ({user?.username})</IonTitle>
          <IonButtons slot="end">
            <IonBadge color={isOnline ? 'success' : 'danger'} style={{ marginRight: '8px' }}>
              {isOnline ? 'Online' : 'Offline'}
            </IonBadge>
            <IonButton onClick={logout}>
              <IonIcon slot="icon-only" icon={logOutOutline} />
            </IonButton>
          </IonButtons>
        </IonToolbar>

        <IonToolbar>
          <IonSearchbar
            value={searchText}
            placeholder="Search by name or team..."
            onIonInput={(e) => {
              setSearchText(e.detail.value || '');
              setVisibleCount(PAGE_SIZE); 
            }}
          />
        </IonToolbar>

        <IonToolbar>
          <IonItem lines="none">
            <IonLabel>Filter:</IonLabel>
            <IonSelect
              value={captainFilter}
              interface="popover"
              onIonChange={(e) => {
                setCaptainFilter(e.detail.value);
                setVisibleCount(PAGE_SIZE); 
              }}
            >
              <IonSelectOption value="all">All Players</IonSelectOption>
              <IonSelectOption value="captains">Only Captains</IonSelectOption>
              <IonSelectOption value="non-captains">Non-Captains</IonSelectOption>
              <IonSelectOption value="unsynced">Not Synced Only</IonSelectOption>
            </IonSelect>
          </IonItem>
        </IonToolbar>
      </IonHeader>

      <IonContent fullscreen className="ion-padding">
        {loading ? (
          <div style={{ display: 'flex', justifyContent: 'center', marginTop: '2rem' }}>
            <IonSpinner name="crescent" />
          </div>
        ) : (
          <>
            <IonList>
              {displayedPlayers.length === 0 ? (
                <IonItem lines="none">
                  <IonLabel color="medium" className="ion-text-center">
                    No players found.
                  </IonLabel>
                </IonItem>
              ) : (
                displayedPlayers.map((player) => (
                  <IonItem
                    button
                    key={player.id}
                    onClick={() => navigate(`/players/${player.id}`)}
                  >
                    <IonAvatar slot="start" style={{ width: '48px', height: '48px' }}>
                      {player.photo ? (
                        <img src={player.photo} alt={player.name} style={{ objectFit: 'cover' }} />
                      ) : (
                        <IonIcon icon={personCircleOutline} style={{ fontSize: '48px', color: '#888' }} />
                      )}
                    </IonAvatar>
                    <IonLabel>
                      <h2>
                        {player.name}
                        {player.isCaptain && (
                          <IonBadge color="warning" style={{ marginLeft: '8px' }}>
                            Captain
                          </IonBadge>
                        )}
                        {player.notSynced && (
                          <IonBadge color="danger" style={{ marginLeft: '8px' }}>
                            Not Synced
                          </IonBadge>
                        )}
                      </h2>
                      <p>{player.team}</p>
                      <IonNote color="medium">
                        PPG: {player.pointsPerGame} | Birth: {player.birthDate.toLocaleDateString()}
                      </IonNote>
                    </IonLabel>
                  </IonItem>
                ))
              )}
            </IonList>

            {hasMorePlayers && (
              <IonButton expand="block" fill="outline" onClick={loadMorePlayers}>
                Load more players
              </IonButton>
            )}

            <IonInfiniteScroll
              disabled={!hasMorePlayers}
              onIonInfinite={loadMoreData}
            >
              <IonInfiniteScrollContent
                loadingSpinner="bubbles"
                loadingText="Loading more players..."
              />
            </IonInfiniteScroll>
          </>
        )}

        <IonFab vertical="bottom" horizontal="end" slot="fixed">
          <IonFabButton ref={fabRef} onClick={() => navigate('/players/new')}>
            <IonIcon icon={add} />
          </IonFabButton>
        </IonFab>

        <IonToast
          isOpen={!!notification}
          message={notification || ''}
          duration={3000}
          onDidDismiss={clearNotification}
          position="top"
          color="secondary"
        />
      </IonContent>
    </IonPage>
  );
};

export default PlayerListPage;