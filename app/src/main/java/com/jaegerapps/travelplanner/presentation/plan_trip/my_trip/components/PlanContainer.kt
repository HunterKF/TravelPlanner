package com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.domain.models.Itinerary.SinglePlan

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
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = spacing.spaceSmall)
                        .size(40.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Red),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Home,
                        null,
                        tint = Color.White
                    )
                }
                Text(
                    text = plan.locationName,
                    style = MaterialTheme.typography.h5
                )
            }
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

@Preview
@Composable
fun SinglePlanPreview() {
    val plan =  SinglePlan(
        address = "Frankfurt",
        locationName = "Alex's House",
        description = "It's a cool place that I have never seen once but will see it by the end of this year.",
        keywords = "cool, dank, hip",
        type = "chill, beer, hip"
    )
    Row(Modifier.fillMaxWidth()) {
        PlanContainer(plan = plan)

    }
}