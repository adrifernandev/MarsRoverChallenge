package com.adrifernandev.marsroverchallenge.ui.navigation

import android.annotation.SuppressLint
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MarsRoverChallengeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier
    ) { _ ->
        NavHost(
            modifier = modifier,
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