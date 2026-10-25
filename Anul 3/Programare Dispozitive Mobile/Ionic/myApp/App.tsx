import React from 'react';
import { Redirect, Route } from 'react-router-dom';
import { IonApp, IonRouterOutlet, setupIonicReact, IonSpinner } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import { AuthProvider, useAuth } from './context/AuthContext';
import { PlayerProvider } from './context/PlayerContext';
import LoginPage from './pages/LoginPage';
import PlayerListPage from './pages/PlayerListPage';
import PlayerDetailPage from './pages/PlayerDetailPage';

import '@ionic/react/css/core.css';
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';
import './theme/variables.css';

setupIonicReact();

const AppRoutes: React.FC = () => {
  const { isAuthenticated, loadingAuth } = useAuth();

  if (loadingAuth) {
    return (
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <IonSpinner name="crescent" />
      </div>
    );
  }

  return (
    <IonRouterOutlet>
      <Route
        exact
        path="/login"
        render={() => (isAuthenticated ? <Redirect to="/players" /> : <LoginPage />)}
      />

      <Route
        exact
        path="/players"
        render={() => (isAuthenticated ? <PlayerListPage /> : <Redirect to="/login" />)}
      />

      <Route
        exact
        path="/players/:id"
        render={() => (isAuthenticated ? <PlayerDetailPage /> : <Redirect to="/login" />)}
      />

      <Route
        exact
        path="/"
        render={() => <Redirect to={isAuthenticated ? "/players" : "/login"} />}
      />
    </IonRouterOutlet>
  );
};

const App: React.FC = () => (
  <AuthProvider>
    <PlayerProvider>
      <IonApp>
        <IonReactRouter>
          <AppRoutes />
        </IonReactRouter>
      </IonApp>
    </PlayerProvider>
  </AuthProvider>
);

export default App;