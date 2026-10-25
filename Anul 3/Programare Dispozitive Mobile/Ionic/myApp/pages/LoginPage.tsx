import React, { useState } from 'react';
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonItem,
  IonInput,
  IonButton,
  IonToast,
  IonCard,
  IonCardHeader,
  IonCardTitle,
  IonCardContent,
} from '@ionic/react';
import { useAuth } from '../context/AuthContext';

const LoginPage: React.FC = () => {
  const { login } = useAuth();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);

  const handleLogin = async () => {
    try {
      await login(username, password);
    } catch (err: any) {
      setError(err.response?.data?.error || 'Eroare la autentificare');
    }
  };

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar color="primary">
          <IonTitle>Euroleague Login</IonTitle>
        </IonToolbar>
      </IonHeader>

      <IonContent className="ion-padding">
        <IonCard style={{ maxWidth: '400px', margin: '40px auto' }}>
          <IonCardHeader>
            <IonCardTitle>Sign In</IonCardTitle>
          </IonCardHeader>
          <IonCardContent>
            <IonItem>
              <IonInput
                label="Username"
                labelPlacement="stacked"
                value={username}
                onIonInput={(e) => setUsername(e.detail.value || '')}
              />
            </IonItem>

            <IonItem>
              <IonInput
                label="Password"
                labelPlacement="stacked"
                type="password"
                value={password}
                onIonInput={(e) => setPassword(e.detail.value || '')}
              />
            </IonItem>

            <IonButton expand="block" style={{ marginTop: '20px' }} onClick={handleLogin}>
              Log In
            </IonButton>
          </IonCardContent>
        </IonCard>

        <IonToast
          isOpen={!!error}
          message={error || ''}
          duration={3000}
          color="danger"
          onDidDismiss={() => setError(null)}
        />
      </IonContent>
    </IonPage>
  );
};

export default LoginPage;