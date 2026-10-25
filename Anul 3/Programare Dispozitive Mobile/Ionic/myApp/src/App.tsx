import { Navigate, Route } from 'react-router-dom';
import { IonApp, IonRouterOutlet, IonSpinner, setupIonicReact } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import { AuthProvider, useAuth } from '../context/AuthContext';
import { PlayerProvider } from '../context/PlayerContext';
import LoginPage from '../pages/LoginPage';
import PlayerListPage from '../pages/PlayerListPage';
import PlayerDetailPage from '../pages/PlayerDetailPage';

/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

/**
 * Ionic Dark Mode
 * -----------------------------------------------------
 * For more info, please see:
 * https://ionicframework.com/docs/theming/dark-mode
 */

/* import '@ionic/react/css/palettes/dark.always.css'; */
/* import '@ionic/react/css/palettes/dark.class.css'; */
import '@ionic/react/css/palettes/dark.system.css';

/* Theme variables */
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
        path="/login"
        element={isAuthenticated ? <Navigate to="/players" replace /> : <LoginPage />}
      />
      <Route
        path="/players"
        element={isAuthenticated ? <PlayerListPage /> : <Navigate to="/login" replace />}
      />
      <Route
        path="/players/:id"
        element={isAuthenticated ? <PlayerDetailPage /> : <Navigate to="/login" replace />}
      />
      <Route
        path="/"
        element={<Navigate to={isAuthenticated ? '/players' : '/login'} replace />}
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
