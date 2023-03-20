package com.jaegerapps.travelplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jaegerapps.travelplanner.navigation.Route
import com.jaegerapps.travelplanner.presentation.SharedViewModel
import com.jaegerapps.travelplanner.presentation.SingleTripScreen
import com.jaegerapps.travelplanner.presentation.home.HomeScreen
import com.jaegerapps.travelplanner.presentation.my_trip.MyTripScreen
import com.jaegerapps.travelplanner.ui.theme.TravelPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelPlannerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val sharedViewModel = SharedViewModel()
                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    val paddingValues = it
                    NavHost(navController = navController, startDestination = Route.Home.route) {
                        composable(Route.Home.route) {
                            HomeScreen {
                                navController.navigate(Route.SingleTrip.route)
                            }
                        }
                        composable(Route.SingleTrip.route) {
                            SingleTripScreen(
                                sharedViewModel = sharedViewModel,
                                onRequestComplete = {
                                navController.navigate(Route.ViewTrip.route) {
                                    popUpToRoute
                                }
                            },

                            )
                        }
                        composable(Route.ViewTrip.route) {
                            MyTripScreen(viewModel = sharedViewModel)
                        }
                    }
                }
            }
        }
    }
}
