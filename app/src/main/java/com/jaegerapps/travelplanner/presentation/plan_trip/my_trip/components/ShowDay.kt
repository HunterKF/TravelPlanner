package com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jaegerapps.travelplanner.core.ui.LocalSpacing

@Composable
fun ShowDay(
    modifier: Modifier = Modifier,
    duration: String,
    currentDay: MutableState<Int>
) {
    val spacing = LocalSpacing.current
    val days = duration.toInt()
    if (days <= 3) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 1..days) {
                Text(
                    text = "Day $i",
                    style = MaterialTheme.typography.h6
                )
            }
        }
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(spacing.spaceLarge)
        ) {
            items(count = days) {
                Box(
                    modifier = Modifier.clickable { currentDay.value = it }
                ) {
                    Text(
                        text = "Day $it",
                        style = MaterialTheme.typography.h6
                    )
                }

            }
        }
    }

}