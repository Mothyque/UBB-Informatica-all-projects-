# Euroleague Basketball App

An Ionic React application for managing Euroleague basketball players and teams.

## 1. Domain & Entity Specification
The main entity represents a **Euroleague Player** with the required property types:
- `id` (String): Unique identifier.
- `name` (String): Full name of the player.
- `team` (String): Current club (e.g. Panathinaikos, Real Madrid).
- `pointsPerGame` (Numeric): Average scoring statistics per game.
- `birthDate` (Date / String): Date of birth (`YYYY-MM-DD` in the date input; serialized as ISO by the API).
- `isCaptain` (Boolean): Flag indicating whether the player is team captain.
- `userId` (String): ID of the authenticated user owning this resource.
- `notSynced` (Boolean): Local flag indicating items created/updated offline awaiting server sync.
- `photo` (String, Optional): Player profile image stored locally (Filesystem) and encoded/uploaded as Base64.
- `latitude` (Numeric, Optional): Arena / team geographic latitude coordinate.
- `longitude` (Numeric, Optional): Arena / team geographic longitude coordinate.

---

## 2. Implemented Features (Lab 1, Lab 2 & Lab 3)

### Phase 1 Features
- **Master-Detail UI:**
  - **Master View (`/players`):** Lists all players with their team, stats, birth date, and captain badge.
  - **Detail View (`/players/:id`):** View details and edit player information, or create a new player (`/players/new`).
- **REST Service Integration:**
  - `GET /api/players` retrieve all players.
  - `GET /api/players/:id` retrieve specific player details.
  - `POST /api/players` add a new player.
  - `PUT /api/players/:id` update player data.
  - `DELETE /api/players/:id` delete a player.
- **WebSockets Real-time Notifications:**
  - Real-time broadcast channel on `ws://localhost:3000`.
  - When an item is added, updated, or deleted, all connected clients receive immediate updates and a visual toast notification.

### Phase 2 Features
- **Network Status Indicator:**
  - Shows real-time network status (`Online` / `Offline`) via a colored header badge.
- **User Authentication (JWT):**
  - Login endpoint (`POST /api/auth/login`) generating JWT tokens.
  - Token is stored in `localStorage` upon authentication.
  - Automatic session restoration on app start (bypasses login if token exists).
  - Logout functionality clearing the stored token and redirecting to `/login`.
- **User-Resource Linking & Scoped WS:**
  - REST endpoints return only resources associated with the authenticated `userId`.
  - Secured WebSocket connection via query token; notifications are only sent to the resource owner.
- **Offline Support & Local Storage:**
  - Data fetched from server is persisted locally in `localStorage`.
  - Offline operations create/update items locally with a visible `Not Synced` badge.
- **Automatic Background Synchronization:**
  - When switching back to Online mode, pending offline items are automatically sent to the server.
- **Pagination (Infinite Scrolling):**
  - Incremental item loading using `<IonInfiniteScroll>`.
- **Search & Filter:**
  - Searchbar filtering players by name or team.
  - Select filter by captain status or unsynced items.

### Phase 3 Features
- **Device Camera Integration:**
  - Capture photos directly inside the player detail / edit view using `@capacitor/camera` and `@ionic/pwa-elements`.
  - Content validation: enforces image formats (JPEG, PNG, WEBP), blocks non-image formats (e.g., PDF) via binary signature inspection, and limits file size (max 5 MB).
- **Filesystem Persistence & Server Upload:**
  - Saved captured photos on the device's persistent storage via `@capacitor/filesystem` (`Directory.Data`).
  - Base64 payload upload to the backend API (`photo` field) and local image avatar rendering in both list and detail views.
- **Interactive Maps & Geolocation:**
  - OpenStreetMap integration via Leaflet and `react-leaflet`.
  - **Location Selection:** Open an interactive modal to pick arena/team geographic coordinates (`latitude` / `longitude`) by tapping anywhere on the map.
  - **Resource Localization:** Reopen the map in view/read-only mode centered directly on the saved player/arena coordinates.
- **Custom UI Animations:**
  - **Component Animation:** Infinite pulse animation (`createAnimation`) applied to the floating action button (`IonFabButton`).
  - **Overridden Component Animation:** Replaced default modal slide transitions with a custom Zoom & Fade-in / Zoom & Fade-out animation for the Map Modal (`enterAnimation` and `leaveAnimation`).

---

## 3. Overall Application Feature Roadmap (Part 1 - 3)
- [x] **Part 1 (Completed):**
  - Master-detail interface.
  - REST client & server architecture.
  - Server-side WebSocket push notifications.
- [x] **Part 2 (Completed):**
  - User authentication (JWT login / registration).
  - Offline support: local data persistence (Storage / SQLite / IndexedDB).
  - Online sync: synchronization between local cache and remote server when connection is restored.
  - Infinite scroll pagination, search, and filtering.
- [x] **Part 3 (Completed):**
  - Camera integration & persistent device filesystem storage.
  - Image validation and upload.
  - Leaflet maps integration for coordinate selection and resource localization.
  - Custom component animation & overridden modal transitions.

---

## 4. How to Run

### Test Credentials:
- `coach1` / `password123`
- `coach2` / `password123`

### 1. Backend Server:
```bash
cd server
npm install
npm run dev
# Server runs on http://localhost:3000 (REST) and ws://localhost:3000 (WebSockets)