import React from 'react';
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
  IonNote
} from '@ionic/react';
import { add, personCircleOutline } from 'ionicons/icons';
import { useNavigate } from 'react-router-dom';
import { usePlayers } from '../context/PlayerContext';

const PlayerListPage: React.FC = () => {
  const navigate = useNavigate();
  const { players, loading, notification, clearNotification } = usePlayers();

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar color="primary">
          <IonTitle>Euroleague Players</IonTitle>
        </IonToolbar>
      </IonHeader>

      <IonContent fullscreen className="ion-padding">
        {loading ? (
          <div style={{ display: 'flex', justifyContent: 'center', marginTop: '2rem' }}>
            <IonSpinner name="crescent" />
          </div>
        ) : (
          <IonList>
            {players.map((player) => (
              <IonItem
                button
                key={player.id}
                onClick={() => navigate(`/players/${player.id}`)}
              >
                <IonIcon icon={personCircleOutline} slot="start" size="large" />
                <IonLabel>
                  <h2>
                    {player.name}
                    {player.isCaptain && (
                      <IonBadge color="warning" style={{ marginLeft: '8px' }}>
                        Captain
                      </IonBadge>
                    )}
                  </h2>
                  <p>{player.team}</p>
                  <IonNote color="medium">
                    PPG: {player.pointsPerGame} | Birth: {player.birthDate}
                  </IonNote>
                </IonLabel>
              </IonItem>
            ))}
          </IonList>
        )}

        <IonFab vertical="bottom" horizontal="end" slot="fixed">
          <IonFabButton onClick={() => navigate('/players/new')}>
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