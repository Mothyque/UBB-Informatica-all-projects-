import React from 'react';
import { Redirect, Route } from 'react-router-dom';
import { IonApp, IonRouterOutlet, setupIonicReact } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import { PlayerProvider } from './context/PlayerContext';
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

const App: React.FC = () => (
  <PlayerProvider>
    <IonApp>
      <IonReactRouter>
        <IonRouterOutlet>
          <Route exact path="/players" render={() => <PlayerListPage />} />
          <Route exact path="/players/:id" render={() => <PlayerDetailPage />} />
          <Route exact path="/">
            <Redirect to="/players" />
          </Route>
        </IonRouterOutlet>
      </IonReactRouter>
    </IonApp>
  </PlayerProvider>
);

export default App;