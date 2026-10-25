# Euroleague Basketball App 

An Ionic React application for managing Euroleague basketball players and teams.

1. ## Domain & Entity Specification
The main entity represents a **Euroleague Player** with the required property types:
- `id` (String): Unique identifier.
- `name` (String): Full name of the player.
- `team` (String): Current club (e.g. Panathinaikos, Real Madrid).
- `pointsPerGame` (Numeric): Average scoring statistics per game.
- `birthDate` (Date): Date of birth (`YYYY-MM-DD` in the date input; serialized as ISO by the API).
- `isCaptain` (Boolean): Flag indicating whether the player is team captain.
- `userId` (String): ID of the authenticated user owning this resource.
- `notSynced` (Boolean): Local flag indicating items created/updated offline awaiting server sync.

---

## 2. Implemented Features (Lab 1 & Lab 2)
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

---

## 3. Overall Application Feature Roadmap (Part 1 - 3)
- **Part 1:**
  - Master-detail interface.
  - REST client & server architecture.
  - Server-side WebSocket push notifications.
- **Part 2:**
  - User authentication (JWT login / registration).
  - Offline support: local data persistence (Storage / SQLite / IndexedDB).
  - Online sync: synchronization between local cache and remote server when connection is restored.
  - Infinite scroll pagination, search, and filtering.
- **Part 3:**
  - External services: show team arena coordinates on Google Maps / send scout reports via Email.
  - Local device services: device camera for player profile photo, motion/haptic sensors.
  - Custom UI animations (page transitions and card gestures).

---

## 4. How to Run

### Test Credentials:
- `coach1` / `password123`
- `coach2` / `password123`

### Backend Server:
```bash
cd server
npm install
npm run dev
# Server runs on http://localhost:3000 (REST) and ws://localhost:3000 (WebSockets)