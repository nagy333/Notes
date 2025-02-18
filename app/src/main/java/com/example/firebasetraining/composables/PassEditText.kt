package com.example.firebasetraining.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyPasswordEditText(
    value: String,
    onValueChange:(String)->Unit,
    visualTransformation: VisualTransformation,
    onShowPassClicked:()->Unit,
    painter: Painter,
    modifier: Modifier = Modifier,
    label_placeholder : String,
    iseError : Boolean = false,

){
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(fontSize = 22.sp),
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color.White,
            unfocusedTrailingIconColor = Color.White,
            focusedTrailingIconColor = Color.White,
            errorTextColor = Color.White,
            errorContainerColor =  MaterialTheme.colorScheme.background,

            ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(onClick = onShowPassClicked) {
                Icon(painter = painter, contentDescription = null)
            }
        },
        isError = iseError,
        label = {
            Text(
                text = label_placeholder,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 10.dp))
        },

        placeholder = {
            Text(
                text = label_placeholder,
                fontSize = 20.sp)
        },
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(8.dp)

    )
}