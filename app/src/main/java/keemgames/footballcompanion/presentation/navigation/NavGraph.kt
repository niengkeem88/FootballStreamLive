package keemgames.footballcompanion.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import keemgames.footballcompanion.presentation.home.HomeScreen
import keemgames.footballcompanion.presentation.match_details.MatchDetailsScreen
import keemgames.footballcompanion.presentation.onboarding.OnboardingScreen
import keemgames.footballcompanion.presentation.splash.SplashScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToDetails = { matchUrl ->
                    navController.navigate(Screen.MatchDetails.createRoute(matchUrl))
                }
            )
        }
        
        composable(
            route = Screen.MatchDetails.route,
            arguments = listOf(navArgument("matchUrl") { type = NavType.StringType })
        ) {
            MatchDetailsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        // Secondary routes (placeholders)
        composable(route = Screen.LiveScores.route) {}
        composable(route = Screen.Highlights.route) {}
        composable(route = Screen.TVGuide.route) {}
        composable(route = Screen.Favorites.route) {}
        composable(route = Screen.Settings.route) {}
    }
}
