package com.example.suivisportsimple.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.suivisportsimple.data.ActivityRepository
import com.example.suivisportsimple.ui.screens.DashboardScreen
import com.example.suivisportsimple.ui.screens.EntryScreen
import com.example.suivisportsimple.ui.screens.MainScreen
import java.time.LocalDate

/**
 * Central navigation host for the application. Defines the destinations and their
 * routes. Dates are passed as ISO‑8601 strings when navigating to the entry screen.
 */
@Composable
fun AppNavHost(repository: ActivityRepository) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController = navController)
        }
        composable(
            route = "entry/{date}",
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) { backStackEntry ->
            val dateString = backStackEntry.arguments?.getString("date") ?: LocalDate.now().toString()
            val date = LocalDate.parse(dateString)
            EntryScreen(navController = navController, repository = repository, date = date)
        }
        composable("dashboard") {
            DashboardScreen(navController = navController, repository = repository)
        }
    }
}