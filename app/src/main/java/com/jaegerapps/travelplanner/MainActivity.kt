package com.jaegerapps.travelplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jaegerapps.travelplanner.navigation.Route
import com.jaegerapps.travelplanner.presentation.plan_trip.SharedViewModel
import com.jaegerapps.travelplanner.presentation.SingleTripScreen
import com.jaegerapps.travelplanner.presentation.plan_trip.duration.DurationScreen
import com.jaegerapps.travelplanner.presentation.home.HomeScreen
import com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.MyTripScreen
import com.jaegerapps.travelplanner.presentation.plan_trip.about_trip.AboutTripScreen
import com.jaegerapps.travelplanner.presentation.plan_trip.interests.InterestsScreen
import com.jaegerapps.travelplanner.presentation.plan_trip.location.LocationScreen
import com.jaegerapps.travelplanner.presentation.plan_trip.replace.ReplaceScreen
import com.jaegerapps.travelplanner.ui.theme.TravelPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
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
                            HomeScreen(
                                onSingleTripNavigate = {
                                    navController.navigate(Route.Location.route)
                                },
                                onMultiTripNavigate = {
                                    sharedViewModel.onMultiDayClick()
                                    navController.navigate(Route.Location.route)
                                }
                            )
                        }
                        composable(Route.SingleTrip.route) {
                            SingleTripScreen(
                                sharedViewModel = sharedViewModel,
                                onRequestComplete = {
                                    navController.navigate(Route.Location.route) {
                                        popUpToRoute
                                    }
                                },

                                )
                        }
                        composable(Route.ViewTrip.route) {
                            MyTripScreen(viewModel = sharedViewModel) {
                                navController.navigate(Route.AddToTrip.route)
                            }
                        }
                        composable(Route.AddToTrip.route) {
                            ReplaceScreen(
                                sharedViewModel = sharedViewModel
                            ) {
                                navController.popBackStack()
                            }
                        }
                        composable(Route.Location.route) {
                            LocationScreen(sharedViewModel = sharedViewModel, onDayTripNext = {
                                navController.navigate(Route.AboutTrip.route)
                            }
                            ) {
                                navController.navigate(Route.Duration.route)

                            }
                        }
                        composable(Route.Duration.route) {
                            DurationScreen(sharedViewModel = sharedViewModel) {
                                navController.navigate(Route.AboutTrip.route)
                            }
                        }
                        composable(Route.AboutTrip.route) {
                            AboutTripScreen(sharedViewModel = sharedViewModel) {
                                navController.navigate(Route.Interests.route)

                            }
                        }
                        composable(Route.Interests.route) {
                            InterestsScreen(sharedViewModel = sharedViewModel) {
                                navController.navigate(Route.ViewTrip.route)

                            }
                        }
                        /*composable(Route.SpecialRequests.route) {
                            SpecialRequestsScreen(sharedViewModel = sharedViewModel) {
                                navController.navigate(Route.FindRestaurant.route)

                            }
                        }
                        composable(Route.FindRestaurant.route) {
                            FindRestaurantsScreen(sharedViewModel = sharedViewModel) {
                                navController.navigate(Route.Transport.route)

                            }
                        }
                        composable(Route.Transport.route) {
                            TransportScreen(sharedViewModel = sharedViewModel) {
                                navController.navigate(Route.ViewTrip.route)
                            }
                        }*/
                    }
                }
            }
        }
    }
}
