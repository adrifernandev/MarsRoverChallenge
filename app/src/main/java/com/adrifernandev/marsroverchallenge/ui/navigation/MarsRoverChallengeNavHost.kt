package com.adrifernandev.marsroverchallenge.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.adrifernandev.marsroverchallenge.presentation.ui.MainScreen
import com.adrifernandev.marsroverchallenge.presentation.viewmodel.MainViewModel

@Composable
fun MarsRoverChallengeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
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
                    MainScreen(
                        viewModel = mainViewModel
                    )
                }
            }
        }
    }
}