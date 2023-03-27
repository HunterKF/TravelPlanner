package com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TripTopBar(
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(
                    2.dp,
                    Color.LightGray
                )
                .shadow(2.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.End
        )
    }
}