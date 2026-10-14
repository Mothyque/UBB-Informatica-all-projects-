# Euroleague Basketball App - Part 1

An Ionic React application for managing Euroleague basketball players and teams.

## 1. Domain & Entity Specification
The main entity represents a **Euroleague Player** with the required property types:
- `id` (String): Unique identifier.
- `name` (String): Full name of the player.
- `team` (String): Current club (e.g. Panathinaikos, Real Madrid).
- `pointsPerGame` (Numeric): Average scoring statistics per game.
- `birthDate` (Date / ISO string): Date of birth (`YYYY-MM-DD`).
- `isCaptain` (Boolean): Flag indicating whether the player is team captain.

---

## 2. Implemented Features (Lab 1)
- **Master-Detail UI:**
  - **Master View (`/players`):** Lists all players with their team, stats, birth date, and captain badge.
  - **Detail View (`/players/:id`):** View details and edit player information, or create a new player (`/players/new`).
- **REST Service Integration:**
  - `GET /api/players` - retrieve all players.
  - `GET /api/players/:id` - retrieve specific player details.
  - `POST /api/players` - add a new player.
  - `PUT /api/players/:id` - update player data.
  - `DELETE /api/players/:id` - delete a player.
- **WebSockets Real-time Notifications:**
  - Real-time broadcast channel on `ws://localhost:3000`.
  - When an item is added, updated, or deleted, all connected clients receive immediate updates and a visual toast notification.

---

## 3. Overall Application Feature Roadmap (Part 1 - 3)
- **Part 1 (Current):**
  - Master-detail interface.
  - REST client & server architecture.
  - Server-side WebSocket push notifications.
- **Part 2:**
  - User authentication (JWT login / registration).
  - Offline support: local data persistence (Storage / SQLite / IndexedDB).
  - Online sync: synchronization between local cache and remote server when connection is restored.
- **Part 3:**
  - External services: show team arena coordinates on Google Maps / send scout reports via Email.
  - Local device services: device camera for player profile photo, motion/haptic sensors.
  - Custom UI animations (page transitions and card gestures).

---

## 4. How to Run

### Backend Server:
```bash
cd server
npm install
npm run dev
# Server runs on http://localhost:3000 (REST) and ws://localhost:3000 (WebSockets)
```