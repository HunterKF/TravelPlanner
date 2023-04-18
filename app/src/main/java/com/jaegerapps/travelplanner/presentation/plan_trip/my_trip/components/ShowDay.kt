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
import androidx.compose.ui.text.font.FontWeight
import com.jaegerapps.travelplanner.core.ui.LocalSpacing

@Composable
fun ShowDay(
    modifier: Modifier = Modifier,
    duration: String,
    currentDay: MutableState<Int>,
    onClick: () -> Unit
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
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.clickable {
                        currentDay.value = i
                        onClick()
                    }
                ) {
                    Text(
                        text = "Day $i",
                        style = MaterialTheme.typography.h6,
                        fontWeight = if (i == currentDay.value) FontWeight.Bold else FontWeight.Normal
                    )
                }

            }
        }
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(spacing.spaceLarge)
        ) {
            items(count = days) {
                val day = it + 1
                Box(
                    modifier = Modifier.clickable { currentDay.value = day
                    println(day)
                    println(currentDay.value)
                    }
                ) {
                    Text(
                        text = "Day $day",
                        style = MaterialTheme.typography.h6,
                        fontWeight = if (day == currentDay.value) FontWeight.Bold else FontWeight.Normal
                    )
                }

            }
        }
    }

}