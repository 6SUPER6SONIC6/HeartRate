package com.supersonic.heartrate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.supersonic.heartrate.screens.homepage.HomepageScreen
import com.supersonic.heartrate.screens.homepage.HomepageScreenDestination
import com.supersonic.heartrate.screens.loading.LoadingScreen
import com.supersonic.heartrate.screens.loading.LoadingScreenDestination
import com.supersonic.heartrate.screens.onboarding.OnboardingScreen
import com.supersonic.heartrate.screens.onboarding.OnboardingScreenDestination
import com.supersonic.heartrate.screens.result.ResultScreen
import com.supersonic.heartrate.screens.result.ResultScreenDestination
import com.supersonic.heartrate.screens.resultHistory.ResultHistoryScreen
import com.supersonic.heartrate.screens.resultHistory.ResultHistoryScreenDestination

@Composable
fun RootAppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = LoadingScreenDestination.route
    ) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        //Loading Screen
        composable(route = LoadingScreenDestination.route) {
            LoadingScreen(
                onNavigationNext = {
                    navController.navigate(OnboardingScreenDestination.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        //Onboarding Screen
        composable(route = OnboardingScreenDestination.route) {
            OnboardingScreen(
                onNavigationToHomepage = {
                    navController.navigate(HomepageScreenDestination.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        //Homepage Screen
        composable(route = HomepageScreenDestination.route) {
            HomepageScreen(
                onNavigationToResult = {
                    navController.navigate(ResultScreenDestination.route)
                }
            )
        }

        //Result Screen
        composable(route = ResultScreenDestination.route) {
            ResultScreen(
                onNavigationToHistory = {
                    navController.navigate(ResultHistoryScreenDestination.route)
                }
            )
        }

        //Result History Screen
        composable(route = ResultHistoryScreenDestination.route) {
            ResultHistoryScreen()
        }

    }
}