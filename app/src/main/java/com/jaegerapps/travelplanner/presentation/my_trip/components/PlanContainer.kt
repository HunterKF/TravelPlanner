package com.jaegerapps.travelplanner.presentation.my_trip.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.domain.models.SinglePlan

@Composable
fun PlanContainer(
    plan: SinglePlan,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    var expandedState by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                expandedState = !expandedState
            }
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = spacing.spaceMedium,
                vertical = spacing.spaceSmall,
            ),
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
                    .padding(spacing.spaceSmall)
            )
            Column(
                modifier = Modifier.animateContentSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = plan.locationName,
                    style = MaterialTheme.typography.h1
                )
                if (expandedState) {
                    Text(
                        text = plan.description,
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = plan.address,
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }
    }
}