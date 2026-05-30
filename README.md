# FootballPulse Live

A football live scores and streaming companion app built with Jetpack Compose and Clean Architecture. Data provided by **TheSportsDB API**.

## Architecture
This project follows Clean Architecture principles with the following layers:
- **data**: Implementation of repositories and data sources (TheSportsDB API via Retrofit, Room).
- **domain**: Business logic and use cases.
- **presentation**: UI components and ViewModels (Jetpack Compose).
- **di**: Dependency injection configuration (Hilt).

## Data Source
- **TheSportsDB API** (v1) – Live scores, match details, team badges, and video highlights
- Major leagues: EPL, Bundesliga, Serie A, Ligue 1, La Liga, Eredivisie, Scottish Premiership

## Technologies
- Kotlin
- Jetpack Compose
- Hilt (DI)
- Retrofit & OkHttp (Networking)
- Room (Local database)
- DataStore (Preferences)
- Navigation Compose
- Coil (Image loading)
- Firebase (Analytics, Crashlytics)
- AppLovin MAX (Ad monetization)

## Getting Started
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle
4. Run on device/emulator (API 26+)

### API Key
TheSportsDB free API key (`3`) is used by default. For production, obtain your own free API key from [thesportsdb.com](https://www.thesportsdb.com/free.php).

## Features
- Live scores from major football leagues
- Match details with team badges and scoreboards
- Video highlight playback via YouTube embeds
- Favorite matches and teams
- League filtering
- Dark theme UI with glassmorphic design
