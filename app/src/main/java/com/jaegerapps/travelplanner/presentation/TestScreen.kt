package com.jaegerapps.travelplanner.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TestScreen(
    viewModel: TestViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
        ) {
            state.responseInfo?.let {
                item {
                    Text(
                        text = state.responseInfo.itinerary.toString()
                    )
                }
            }

        }
        Button(onClick = { viewModel.onSend() }) {
            Text(
                text = "Send"
            )
        }
        TextField(
            value = viewModel.value.value,
            onValueChange = { newValue -> viewModel.onValueChannge(newValue) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    viewModel.onSend()
                }
            )
        )
    }
}