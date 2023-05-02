package com.jaegerapps.travelplanner.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun HomeScreen(onSingleTripNavigate: () -> Unit, onMultiTripNavigate: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val uri = "https://lh3.googleusercontent.com/places/AJQcZqI3jiwF411q2k73B6SC84M-tns0zSeDE4PEu4UqYXLei5TZWh9RqhtNKvcSkAxYM0T1V2EplhV230uulNmpjgyQHfN2E-BL9_0=s1600-w400"
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = uri)
                    .crossfade(true)
                    .build(),
                contentDescription = null
                )


            Button(onClick = { onSingleTripNavigate() }) {
                Text(
                    text = "Plan single trip"
                )
            }
            Button(onClick = { onMultiTripNavigate() }) {
                Text(
                    text = "Plan multi trip"
                )
            }
        }
    }
}