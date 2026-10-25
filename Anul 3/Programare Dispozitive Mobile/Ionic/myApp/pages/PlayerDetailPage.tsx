import React, { useEffect, useState } from 'react';
import {
  IonContent,
  IonHeader,
  IonPage,
  IonTitle,
  IonToolbar,
  IonButtons,
  IonBackButton,
  IonItem,
  IonInput,
  IonToggle,
  IonButton,
  IonToast,
  IonSpinner
} from '@ionic/react';
import { useNavigate, useParams } from 'react-router-dom';
import { getPlayerById } from '../services/playerApi';
import { PlayerInput } from '../models/Player';
import { usePlayers } from '../context/PlayerContext';

const toDateInputValue = (date: Date): string => date.toISOString().split('T')[0];

const PlayerDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { savePlayer, removePlayer, players } = usePlayers();
  const isNew = id === 'new';

  const [formData, setFormData] = useState<PlayerInput>({
    name: '',
    team: '',
    pointsPerGame: 0,
    birthDate: new Date(),
    isCaptain: false,
  });

  const [loading, setLoading] = useState<boolean>(!isNew);
  const [toastMessage, setToastMessage] = useState<string | null>(null);

  useEffect(() => {
    if (!isNew && id) {
      const existing = players.find((p) => p.id === id);
      if (existing) {
        setFormData({
          name: existing.name,
          team: existing.team,
          pointsPerGame: existing.pointsPerGame,
          birthDate: existing.birthDate,
          isCaptain: existing.isCaptain,
        });
        setLoading(false);
      } else {
        getPlayerById(id)
          .then((data) => {
            setFormData({
              name: data.name,
              team: data.team,
              pointsPerGame: data.pointsPerGame,
              birthDate: data.birthDate,
              isCaptain: data.isCaptain,
            });
          })
          .catch(() => setToastMessage('Nu s-a putut încărca de pe server'))
          .finally(() => setLoading(false));
      }
    }
  }, [id, isNew, players]);

  const handleSave = async () => {
    try {
      await savePlayer(formData, isNew ? undefined : id);
      navigate(-1);
    } catch (err) {
      setToastMessage('Eroare la salvare');
    }
  };

  const handleDelete = async () => {
    if (isNew || !id) return;
    try {
      await removePlayer(id);
      navigate(-1);
    } catch (err) {
      setToastMessage('Eroare la ștergere');
    }
  };

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar color="primary">
          <IonButtons slot="start">
            <IonBackButton defaultHref="/players" />
          </IonButtons>
          <IonTitle>{isNew ? 'New Euroleague Player' : 'Player Details'}</IonTitle>
        </IonToolbar>
      </IonHeader>

      <IonContent className="ion-padding">
        {loading ? (
          <div style={{ display: 'flex', justifyContent: 'center', marginTop: '2rem' }}>
            <IonSpinner name="crescent" />
          </div>
        ) : (
          <div>
            <IonItem>
              <IonInput
                label="Name (String)"
                labelPlacement="stacked"
                value={formData.name}
                placeholder="ex. Kostas Sloukas"
                onIonInput={(e) => setFormData({ ...formData, name: e.detail.value || '' })}
              />
            </IonItem>

            <IonItem>
              <IonInput
                label="Team (String)"
                labelPlacement="stacked"
                value={formData.team}
                placeholder="ex. Panathinaikos"
                onIonInput={(e) => setFormData({ ...formData, team: e.detail.value || '' })}
              />
            </IonItem>

            <IonItem>
              <IonInput
                label="Points Per Game (Numeric)"
                labelPlacement="stacked"
                type="number"
                value={formData.pointsPerGame}
                onIonInput={(e) =>
                  setFormData({ ...formData, pointsPerGame: parseFloat(e.detail.value || '0') })
                }
              />
            </IonItem>

            <IonItem>
              <IonInput
                label="Birth Date (Date)"
                labelPlacement="stacked"
                type="date"
                value={toDateInputValue(formData.birthDate)}
                onIonInput={(e) =>
                  setFormData({ ...formData, birthDate: new Date(e.detail.value || '') })
                }
              />
            </IonItem>

            <IonItem>
              <IonToggle
                checked={formData.isCaptain}
                onIonChange={(e) => setFormData({ ...formData, isCaptain: e.detail.checked })}
              >
                Team Captain (Boolean)
              </IonToggle>
            </IonItem>

            <div style={{ marginTop: '2rem' }}>
              <IonButton expand="block" onClick={handleSave}>
                Save Player
              </IonButton>

              {!isNew && (
                <IonButton
                  expand="block"
                  color="danger"
                  fill="outline"
                  style={{ marginTop: '0.8rem' }}
                  onClick={handleDelete}
                >
                  Delete Player
                </IonButton>
              )}
            </div>
          </div>
        )}

        <IonToast
          isOpen={!!toastMessage}
          message={toastMessage || ''}
          duration={2500}
          onDidDismiss={() => setToastMessage(null)}
          color="danger"
        />
      </IonContent>
    </IonPage>
  );
};

export default PlayerDetailPage;