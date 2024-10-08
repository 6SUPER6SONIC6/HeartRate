package com.supersonic.heartrate.navigation

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.supersonic.heartrate.screens.history.HistoryScreen
import com.supersonic.heartrate.screens.history.HistoryScreenDestination
import com.supersonic.heartrate.screens.history.HistoryViewModel
import com.supersonic.heartrate.screens.homepage.HomepageScreen
import com.supersonic.heartrate.screens.homepage.HomepageScreenDestination
import com.supersonic.heartrate.screens.loading.LoadingScreen
import com.supersonic.heartrate.screens.loading.LoadingScreenDestination
import com.supersonic.heartrate.screens.measurement.MeasurementScreen
import com.supersonic.heartrate.screens.measurement.MeasurementScreenDestination
import com.supersonic.heartrate.screens.measurement.MeasurementViewModel
import com.supersonic.heartrate.screens.onboarding.OnboardingScreen
import com.supersonic.heartrate.screens.onboarding.OnboardingScreenDestination
import com.supersonic.heartrate.screens.result.ResultScreen
import com.supersonic.heartrate.screens.result.ResultScreenDestination
import com.supersonic.heartrate.screens.result.ResultScreenViewModel

@Composable
fun RootAppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomepageScreenDestination.route
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
                    navController.navigate(HomepageScreenDestination.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        //Onboarding Screen
        composable(route = OnboardingScreenDestination.route) {
            val context = LocalContext.current
            OnboardingScreen(
                onOnboardingFinish = {
                    saveOnboardingCompleted(context)
                    navController.navigate(MeasurementScreenDestination.route) {
                        popUpTo(HomepageScreenDestination.route)
                    }
                }
            )
        }

        //Homepage Screen
        composable(route = HomepageScreenDestination.route) {
            val context = LocalContext.current
            HomepageScreen(
                needDisplayOnboarding = isFirstRun(context),
                onNavigationNext = {
                    if (isFirstRun(context)) navController.navigate(OnboardingScreenDestination.route)
                    else navController.navigate(MeasurementScreenDestination.route)
                                         },
                onNavigateToResultHistory = {
                    navController.navigate(HistoryScreenDestination.route)
                }
            )
        }

        // Measurement Screen
        composable(route = MeasurementScreenDestination.route) {
            val viewModel = hiltViewModel<MeasurementViewModel>()
            MeasurementScreen(
                viewModel = viewModel,
                onNavigateToResultHistory = {
                    navController.navigate(HistoryScreenDestination.route)
                },
                onNavigationToResult = {
                    navController.navigate("${ResultScreenDestination.route}/${it}")
                },
                onNavigateBack = { navController.navigateUp() }
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
                    navController.navigate(HistoryScreenDestination.route)
                }
            )
        }

        //Result History Screen
        composable(route = HistoryScreenDestination.route) {
            val viewModel = hiltViewModel<HistoryViewModel>()
            HistoryScreen(
                modifier = Modifier.fillMaxSize(),
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

const val PREFS_NAME = "onboarding_prefs"
const val KEY_FIRST_RUN = "is_first_run"

fun saveOnboardingCompleted(context: Context) {
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(KEY_FIRST_RUN, false)
    editor.apply()
}

fun isFirstRun(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(KEY_FIRST_RUN, true)
}