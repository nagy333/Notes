package com.example.firebasetraining.composables

import android.content.Context
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MyButton(
    navController: NavController,
    context: Context,
    onClick:(NavController,Context)->Unit,
    text : String,
    modifier: Modifier = Modifier

){
    Button(
        onClick = { onClick(navController,context) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = modifier
            .wrapContentSize()) {
        Text(
            text = text,
            fontSize = 25.sp)
    }
}