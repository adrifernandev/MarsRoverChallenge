package com.adrifernandev.marsroverchallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MarsRoverChallengeApp() {
    val navController = rememberNavController()

    MarsRoverChallengeNavHost(
        navController = navController
    )
}