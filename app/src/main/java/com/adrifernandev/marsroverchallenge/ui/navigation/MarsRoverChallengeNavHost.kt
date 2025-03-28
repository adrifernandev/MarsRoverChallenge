package com.adrifernandev.marsroverchallenge.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

@Composable
fun MarsRoverChallengeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        NavHost(
            modifier = modifier
                .padding(paddingValues),
            navController = navController,
            startDestination = NavigationRoutes.MainGraph
        ) {

            //Main Graph
            navigation<NavigationRoutes.MainGraph>(
                startDestination = NavigationRoutes.Main
            ) {
                composable<NavigationRoutes.Main> {

                }
            }
        }
    }
}