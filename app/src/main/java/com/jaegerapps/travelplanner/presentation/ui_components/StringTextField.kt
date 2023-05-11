package com.jaegerapps.travelplanner.presentation.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaegerapps.travelplanner.core.ui.LocalSpacing

@Composable
fun StringTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeHolderText: String = "",
    icon: ImageVector? = null,
    onValueChange: (String) -> Unit,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    textStyle: TextStyle = TextStyle(
        color = MaterialTheme.colors.primaryVariant,
        fontSize = 20.sp
    ),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor),
        horizontalArrangement = horizontalArrangement
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            leadingIcon = {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            },
            trailingIcon = trailingIcon,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            singleLine = true,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .alignBy(LastBaseline),
            colors = TextFieldDefaults.textFieldColors(
                textColor = textColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            ),
            maxLines = 3,
            placeholder = {
                Text(text = placeHolderText)
            }

        )
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
    }
}