package com.example.jobs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jobs.ui.SplashScreen

@Composable
fun Navigation(navController: NavHostController, viewModel: JobViewModel) {
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") { SplashScreen(navController = navController) }
        composable("jobList") {
            JobListScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            "jobDetail/{jobId}",
            arguments = listOf(
                navArgument("jobId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            JobDetailScreen(
                jobId = backStackEntry.arguments?.getInt("jobId") ?: 0,
                navController = navController,
                viewModel = viewModel
            )
        }
        composable("createJob") {
            CreateJobScreen(navController = navController, viewModel = viewModel)
        }
        composable("myJobList") {
            MyJobListScreen(navController = navController, viewModel = viewModel)
        }
        composable("favoriteJobList") {
            FavoriteJobListScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            "editJob/{jobId}",
            arguments = listOf(
                navArgument("jobId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            EditJobScreen(
                jobId = backStackEntry.arguments?.getInt("jobId") ?: 0,
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            "applyJob/{jobId}",
            arguments = listOf(
                navArgument("jobId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            ApplyJobScreen(navController = navController)
        }
    }
}