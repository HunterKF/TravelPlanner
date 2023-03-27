package com.jaegerapps.travelplanner.presentation.plan_trip.special_requests

import android.graphics.Paint.Align
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.domain.models.SpecialRequest
import com.jaegerapps.travelplanner.presentation.ui_components.ActionButton

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpecialRequestContainer(
    modifier: Modifier = Modifier,
    specialRequestsViewModel: SpecialRequestsViewModel,
    totalDays: Int,
) {
    val state = specialRequestsViewModel.state.value
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.forEach { request ->
            var expandedState by remember {
                mutableStateOf(false)
            }
            var string by remember {
                mutableStateOf("")
            }
            Box(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(100.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(
                                top = spacing.spaceMedium,
                                bottom = spacing.spaceMedium,
                                start = spacing.spaceMedium
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        ExposedDropdownMenuBox(
                            modifier = Modifier.width(IntrinsicSize.Max),
                            expanded = expandedState,
                            onExpandedChange = {
                                expandedState = !expandedState
                            }) {

                            Row {
                                Text(
                                    text = "Day ${request.day}"
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    modifier = Modifier.alignBy(
                                        LastBaseline
                                    )
                                )
                                Spacer(Modifier.width(spacing.spaceExtraSmall))
                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .background(Color.DarkGray)
                                        .fillMaxHeight()
                                )
                            }

                            ExposedDropdownMenu(
                                modifier = Modifier.width(80.dp),
                                expanded = expandedState,
                                onDismissRequest = { expandedState = false }
                            ) {
                                // this is a column scope
                                // all the items are added vertically
                                for (i in 1..totalDays) {
                                    DropdownMenuItem(onClick = {
                                        specialRequestsViewModel.onDayChange(request.id, i)
                                        expandedState = false
                                    }) {
                                        Text(text = "Day $i")
                                    }
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        TextField(
                            modifier = Modifier.fillMaxSize(),
                            value = string,
                            onValueChange = { newValue ->
                                string = newValue
                                specialRequestsViewModel.onStringChange(
                                    request.id,
                                    string
                                )
                            },
                            placeholder = {
                                Text(
                                    text = "What would you like?"
                                )
                            },
                            shape = RoundedCornerShape(0.dp),
                            trailingIcon = {
                                IconButton(onClick = {
                                    if (state.size == 1) Toast.makeText(
                                        context,
                                        "Need to have at least one request.",
                                        Toast.LENGTH_SHORT
                                    ).show() else specialRequestsViewModel.onRequestRemove(request)
                                }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        null
                                    )
                                }

                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            maxLines = 3,
                            textStyle = MaterialTheme.typography.body1,
                        )
                    }
                }
            }
            if (request.id != state.size) {
                Spacer(Modifier.height(spacing.spaceMedium))
            }

        }
        Spacer(Modifier.height(spacing.spaceMedium))
        ActionButton(text = "Add more", onClick = { specialRequestsViewModel.onRequestAdd() })
        if (state.size >= totalDays * 1.5) {
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Text(
                text = "Careful Adding too many requests to your trip. The more you add the harder it will be to make the perfect trip.",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
    }

}

@Preview
@Composable
fun SpecialRequestContainerPreview() {
    val specialRequestsViewModel = SpecialRequestsViewModel()

    SpecialRequestContainer(specialRequestsViewModel = specialRequestsViewModel, totalDays = 4)
}