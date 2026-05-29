package keemgames.footballcompanion.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object LiveScores : Screen("live_scores")
    object Highlights : Screen("highlights")
    object TVGuide : Screen("tv_guide")
    object Favorites : Screen("favorites")
    object Settings : Screen("settings")
    
    object MatchDetails : Screen("match_details/{matchUrl}") {
        fun createRoute(matchUrl: String) = "match_details/$matchUrl"
    }
}
