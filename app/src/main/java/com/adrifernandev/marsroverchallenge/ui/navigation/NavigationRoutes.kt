package com.adrifernandev.marsroverchallenge.ui.navigation

import kotlinx.serialization.Serializable

sealed class NavigationRoutes {

    //Main Rover Navigation Graph
    @Serializable
    data object MainGraph : NavigationRoutes()

    @Serializable
    data object Main : NavigationRoutes()
}