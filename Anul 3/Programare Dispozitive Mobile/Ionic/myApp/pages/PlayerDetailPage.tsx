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
  IonSpinner,
  IonAvatar,
  IonIcon
} from '@ionic/react';
import { useNavigate, useParams } from 'react-router-dom';
import { getPlayerById } from '../services/playerApi';
import { PlayerInput } from '../models/Player';
import { usePlayers } from '../context/PlayerContext';
import { cameraOutline, personCircleOutline, locationOutline, mapOutline } from 'ionicons/icons';
import { takePhoto } from '../services/photoService';
import { MapModal } from '../src/components/MapModal';

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
    photo: '',
    latitude: undefined,
    longitude: undefined
  });

  const [loading, setLoading] = useState<boolean>(!isNew);
  const [toastColor, setToastColor] = useState<'success' | 'danger'>('danger');
  const [toastMessage, setToastMessage] = useState<string | null>(null);
  const [isMapOpen, setIsMapOpen] = useState<boolean>(false);
  const [isMapReadOnly, setIsMapReadOnly] = useState<boolean>(false);


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
          photo: existing.photo || '',
          latitude: existing.latitude,
          longitude: existing.longitude
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
              photo: data.photo || '',
              latitude: data.latitude,
              longitude: data.longitude
            });
          })
          // .catch(() => setToastMessage('Nu s-a putut încărca de pe server'))
          .finally(() => setLoading(false));
      }
    }
  }, [id, isNew, players]);

  const handleSave = async () => {
    try {
      await savePlayer(formData, isNew ? undefined : id);
      setToastColor('success');
      setToastMessage('Salvare reușită!');
      navigate(-1);
    } catch (err) {
      console.error('Eroare la salvare:', err);
      setToastColor('danger');
      setToastMessage('Eroare la salvare');
    }
  };

  const handleDelete = async () => {
    if (isNew || !id) return;
    try {
      await removePlayer(id);
      setToastColor('success');
      setToastMessage('Ștergere reușită!');
      navigate(-1);
    } catch (err) {
      console.error('Eroare la ștergere:', err);
      setToastColor('danger');
      setToastMessage('Eroare la ștergere');
    }
  };

  const handleTakePhoto = async () => {
    try {
      const photo = await takePhoto();
      if(photo.base64) {
        setFormData((prev) => ({ ...prev, photo: photo.base64 || '' }));
        setToastColor('success');
        setToastMessage('Poza a fost adăugată cu succes!');
      }
    } catch (err: any) {
      const message = err?.message || '';
      if (message.includes('User cancelled')) {
        return; 
      }
      console.error('Eroare la capturarea pozei:', err);
      setToastColor('danger');
      setToastMessage('Eroare la capturarea pozei');
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
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', margin: '1rem 0' }}>
              <IonAvatar style={{ width: '100px', height: '100px', boxShadow: '0 4px 8px rgba(0,0,0,0.2)' }}>
                {formData.photo ? (
                  <img src={formData.photo} alt={formData.name} style={{ objectFit: 'cover', width: '100%', height: '100%' }} />
                ) : (
                  <IonIcon icon={personCircleOutline} style={{ fontSize: '100px', color: '#888' }} />
                )}
              </IonAvatar>

              <IonButton fill="outline" size="small" onClick={handleTakePhoto} style={{ marginTop: '0.8rem' }}>
                <IonIcon slot="start" icon={cameraOutline} />
                {formData.photo ? 'Change Photo' : 'Take / Upload Photo'}
              </IonButton>
            </div>
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

            <IonItem lines="none" style={{ marginTop: '1rem' }}>
              <div style={{ width: '100%' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <span style={{ fontSize: '0.9rem', color: '#888' }}>Team Location:</span>
                  <span style={{ fontSize: '0.85rem', fontWeight: 600 }}>
                    {formData.latitude && formData.longitude
                      ? `${formData.latitude.toFixed(4)}, ${formData.longitude.toFixed(4)}`
                      : 'Not set'}
                  </span>
                </div>

                <div style={{ display: 'flex', gap: '8px', marginTop: '8px' }}>
                  <IonButton
                    expand="block"
                    fill="outline"
                    size="small"
                    style={{ flex: 1 }}
                    onClick={() => {
                      setIsMapReadOnly(false);
                      setIsMapOpen(true);
                    }}
                  >
                    <IonIcon slot="start" icon={locationOutline} />
                    Select Location
                  </IonButton>

                  {formData.latitude && formData.longitude && (
                    <IonButton
                      expand="block"
                      fill="solid"
                      size="small"
                      color="secondary"
                      style={{ flex: 1 }}
                      onClick={() => {
                        setIsMapReadOnly(true);
                        setIsMapOpen(true);
                      }}
                    >
                      <IonIcon slot="start" icon={mapOutline} />
                      View on Map
                    </IonButton>
                  )}
                </div>
              </div>
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
          color={toastColor}
        />

        <MapModal
          isOpen={isMapOpen}
          readOnly={isMapReadOnly}
          initialLat={formData.latitude}
          initialLng={formData.longitude}
          onClose={() => setIsMapOpen(false)}
          onSelectLocation={(lat, lng) => {
            setFormData((prev) => ({ ...prev, latitude: lat, longitude: lng }));
            setToastColor('success');
            setToastMessage('Locația a fost salvată în formular!');
          }}
        />
      </IonContent>
    </IonPage>
  );
};

export default PlayerDetailPage;