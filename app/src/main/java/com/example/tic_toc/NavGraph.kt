package com.example.tic_toc

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "board_selection"
    ) {
        composable("board_selection") {
            BoardSelectionScreen(navController)
        }

        composable(
            "game/{size}",
            arguments = listOf(navArgument("size") { type = NavType.IntType })
        ) { backStackEntry ->
            val size = backStackEntry.arguments?.getInt("size") ?: 3
            GameScreen(boardSize = size, navController = navController)
        }
    }
}
