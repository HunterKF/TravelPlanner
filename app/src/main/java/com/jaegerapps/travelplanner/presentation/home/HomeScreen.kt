package com.jaegerapps.travelplanner.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(onSingleTripNavigate: () -> Unit, onMultiTripNavigate: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { onSingleTripNavigate() }) {
                Text(
                    text = "Plan single trip"
                )
            }
            Button(onClick = { onMultiTripNavigate() }) {
                Text(
                    text = "Plan single trip"
                )
            }
        }
    }
}