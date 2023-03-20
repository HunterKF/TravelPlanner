package com.jaegerapps.travelplanner.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Route(val route: String, val label: String, val vector: ImageVector) {
    object Home : Route(route = "home", "home", Icons.Default.Home)
    object SingleTrip : Route(route = "single_trip", "Planner", Icons.Default.AccountBox)
    object ViewTrip : Route (route = "view_trip", "My Trip", Icons.Default.AccountCircle)
}