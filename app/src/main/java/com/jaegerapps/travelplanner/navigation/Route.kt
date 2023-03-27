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
    object Location : Route (route = "location", "Location", Icons.Default.AccountCircle)
    object Duration : Route (route = "duration", "Duration", Icons.Default.AccountCircle)
    object AboutTrip : Route (route = "about_trip", "About Trip", Icons.Default.AccountCircle)
    object Interests : Route (route = "interests", "Interest", Icons.Default.AccountCircle)
    object SpecialRequests : Route (route = "special_requests", "Special Requests", Icons.Default.AccountCircle)

    object FindRestaurant : Route (route = "find_restaurants", "Find Restaurants", Icons.Default.AccountCircle)
    object Transport : Route (route = "transport", "Transport", Icons.Default.AccountCircle)
}