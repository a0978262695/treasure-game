# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

### Frontend
```bash
npm install       # install dependencies
npm run dev       # start dev server at http://localhost:3000 (auto-opens browser)
npm run build     # production build → build/ directory
```

### Backend
```bash
cd backend
mvn spring-boot:run   # start Spring Boot server at http://localhost:8080
mvn package           # build → target/treasure-game-backend-0.0.1-SNAPSHOT.jar
java -jar target/treasure-game-backend-0.0.1-SNAPSHOT.jar  # run the jar
```

Both servers must run simultaneously for the app to work. No test runner is configured.

## Architecture

**Interactive Treasure Box Game** — React + TypeScript frontend (Vite) + Spring Boot backend, communicating over REST with JWT auth.

### Auth flow
1. `AuthProvider` (`src/context/AuthContext.tsx`) wraps the app and persists `token` + `authUser` in `localStorage`
2. `apiFetch` (`src/lib/api.ts`) is the single fetch wrapper — it always reads the token from localStorage and attaches it as `Authorization: Bearer <token>`
3. `App.tsx` renders `<AuthForm />` when `user` is null; once signed in, the game initializes
4. On game end, the score is saved via `POST /api/games/result` (fire-and-forget, errors are swallowed)

### Backend structure (`backend/src/main/java/com/treasuregame/`)
- **Spring Boot 3.3.5, Java 21, SQLite** — database file at `backend/treasure_game.db`; schema auto-managed by `spring.jpa.hibernate.ddl-auto=update`
- `security/JwtAuthFilter` — validates JWT on every request; the authenticated principal is the numeric `userId` (Long)
- `controller/` — `AuthController` (`/api/auth`), `GameController` (`/api/games`), `LeaderboardController` (`/api/leaderboard`)
- `service/AuthService` — handles signup/signin/signout; invalidates sessions by deleting the `Session` row on signout
- `model/` — `User` (email, passwordHash, displayName, totalScore, gamesPlayed), `GameResult`, `Session`
- JWT secret and expiration are in `application.properties` (not env-based in dev)

### Frontend game logic (`src/App.tsx`)
All game state lives in `App`. The `Box` interface is `{ id, isOpen, hasTreasure }`. On each game:
- 3 boxes created, one randomly assigned `hasTreasure: true`
- Clicking a closed box opens it: treasure → +$100, skeleton → -$50; appropriate sound plays
- Game ends when the treasure box is opened or all boxes are opened
- `scoreRef` / `boxesRef` capture final values for the post-game save effect (avoids stale closure over state)

### UI components
`src/components/ui/` — full shadcn/ui library (Radix UI + Tailwind). `src/components/figma/ImageWithFallback.tsx` — image with inline SVG fallback. `src/components/AuthForm.tsx` and `src/components/Leaderboard.tsx` are app-specific.

### Styling
- **Do not edit `src/index.css`** — compiled Tailwind v4 output that gets regenerated
- CSS variables, theme tokens, and custom styles go in **`src/styles/globals.css`**

### Key conventions
- Import path alias `@` resolves to `./src`
- Animations use `motion/react` (`import { motion } from 'motion/react'`)
- `vite.config.ts` has version-pinned package aliases for all UI dependencies — intentional for Figma-exported projects
- Assets: game images in `src/assets/`, sounds in `src/audios/`, result images in `src/results/`
