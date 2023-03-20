package com.jaegerapps.travelplanner.presentation.my_trip.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.domain.models.TransportationDetails

@Composable
fun TransportContainer(
    modifier: Modifier = Modifier,
    transport: TransportationDetails
) {
    val spacing = LocalSpacing.current

    var expandedState by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .background(MaterialTheme.colors.surface)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                expandedState = !expandedState
            }
    ) {
        Column(
            modifier = Modifier
                .padding(spacing.spaceMedium)
                .animateContentSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.width(60.dp)
                ) {
                    Box(
                        modifier = Modifier.size(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Notifications,
                            null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = transport.startingLocation,
                        style = MaterialTheme.typography.subtitle1
                    )

                }
                Column(
                    modifier = Modifier.width(60.dp)
                ) {
                    Box(
                        modifier = Modifier.size(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Notifications,
                            null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = transport.endingLocation,
                        style = MaterialTheme.typography.subtitle1
                    )

                }
            }
            if (expandedState) {
                Column(Modifier.fillMaxWidth()) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Starting address"
                        )
                        Text(
                            text = transport.startingAddress
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Ending Address"
                        )
                        Text(
                            text = transport.endingAddress
                        )
                    }
                    Column {
                        Text(text = "Directions")
                        Text(
                            text = transport.directions
                        )
                    }
                }
            }
        }
    }
}