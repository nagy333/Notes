package com.example.firebasetraining.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyEditText(
    value : String,
    onValueChange:(String)->Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean =false,
    placeHolder : String,
    textStyle: TextStyle = TextStyle(fontSize = 22.sp),

    ){
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        readOnly = readOnly,
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
            ),

        placeholder = {
            Text(
                text = placeHolder,
                fontSize = 22.sp)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)

    )
}