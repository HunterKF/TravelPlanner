package com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jaegerapps.travelplanner.R
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
    val animateRatio by animateFloatAsState(
        targetValue = if (expandedState) 1f else 1.7f, animationSpec = tween(
            durationMillis = 200
        )
    )

    Box(
        modifier = modifier
            .background(MaterialTheme.colors.surface)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                expandedState = !expandedState
                println(plan.photoRef)
            }
    ) {

        Box(
            modifier = modifier
                .aspectRatio(animateRatio)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                if (plan.photoRef.isNullOrBlank()) {
                    /*Box(
                        modifier = Modifier
                            .shimmerEffect()
                            .fillMaxSize(),
                    )*/
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(id = R.drawable.picture_not_found),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                } else {

                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(data = plan.photoRef)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }

                /*Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.namsan_tower),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )*/
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color(0x99000000))
                        )
                    )
                    .padding(spacing.spaceMedium)
                    .animateContentSize()

                    .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
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
                        style = MaterialTheme.typography.h5,
                        color = Color.White

                    )
                }
                if (expandedState) {
                    Text(
                        text = plan.description,
                        style = MaterialTheme.typography.body1,
                        color = Color.White
                    )
                    Text(
                        text = plan.address,
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.White

                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SinglePlanPreview() {
    val uri =
        "https://lh3.googleusercontent.com/places/AJQcZqI3jiwF411q2k73B6SC84M-tns0zSeDE4PEu4UqYXLei5TZWh9RqhtNKvcSkAxYM0T1V2EplhV230uulNmpjgyQHfN2E-BL9_0=s1600-w400"

    val plan = SinglePlan(
        address = "Frankfurt",
        photoRef = null,
        locationName = "Alex's House",
        description = "It's a cool place that I have never seen once but will see it by the end of this year.",
        keywords = "cool, dank, hip",
        type = "chill, beer, hip"
    )
    Row(Modifier.fillMaxWidth()) {
        PlanContainer(plan = plan)

    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}