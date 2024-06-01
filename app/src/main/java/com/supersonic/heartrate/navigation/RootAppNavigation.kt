package com.supersonic.heartrate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.supersonic.heartrate.screens.homepage.HomepageScreen
import com.supersonic.heartrate.screens.homepage.HomepageScreenDestination
import com.supersonic.heartrate.screens.homepage.HomepageViewModel
import com.supersonic.heartrate.screens.loading.LoadingScreen
import com.supersonic.heartrate.screens.loading.LoadingScreenDestination
import com.supersonic.heartrate.screens.onboarding.OnboardingScreen
import com.supersonic.heartrate.screens.onboarding.OnboardingScreenDestination
import com.supersonic.heartrate.screens.result.ResultScreen
import com.supersonic.heartrate.screens.result.ResultScreenDestination
import com.supersonic.heartrate.screens.result.ResultScreenViewModel
import com.supersonic.heartrate.screens.resultHistory.ResultHistoryScreen
import com.supersonic.heartrate.screens.resultHistory.ResultHistoryScreenDestination
import com.supersonic.heartrate.screens.resultHistory.ResultHistoryViewModel

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
            val viewModel = hiltViewModel<HomepageViewModel>()
            HomepageScreen(
                viewModel = viewModel,
                onNavigateToResultHistory = {
                    navController.navigate(ResultHistoryScreenDestination.route)
                },
                onNavigationToResult = {
                    navController.navigate("${ResultScreenDestination.route}/${it}")
                }
            )
        }

        //Result Screen
        composable(
            route = ResultScreenDestination.routeWithArgs,
            arguments = listOf(navArgument("heartRateId"){
            type = NavType.IntType
        })) {
            val viewModel = hiltViewModel<ResultScreenViewModel>()
            ResultScreen(
                viewModel = viewModel,
                onNavigateToHomepage = {
                    navController.navigate(HomepageScreenDestination.route){
                        popUpTo(0)
                    }
                },
                onNavigationToHistory = {
                    navController.navigate(ResultHistoryScreenDestination.route)
                }
            )
        }

        //Result History Screen
        composable(route = ResultHistoryScreenDestination.route) {
            val viewModel = hiltViewModel<ResultHistoryViewModel>()
            ResultHistoryScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.navigate(HomepageScreenDestination.route){
                        popUpTo(0)
                    }
                }
            )
        }

    }
}