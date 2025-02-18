package com.example.firebasetraining.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAlertDialog(
    context: Context,
    noteTitle : String,
    onTitleChange:(String)->Unit,
    noteBody : String,
    onBodyChange:(String)->Unit,
    onSaveClicked:(Context)->Unit,
    onDismissRequest:()->Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .fillMaxWidth()
            .height(450.dp)
            .background(MaterialTheme.colorScheme.background)
            .clip(RoundedCornerShape(15.dp)),
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,

                )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                val (titleField, bodyField, btnSave) = createRefs()
                MyEditText(
                    value = noteTitle,
                    onValueChange = onTitleChange,
                    placeHolder = "Title",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .constrainAs(titleField) {
                        top.linkTo(parent.top,15.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                MyEditText(
                    value = noteBody,
                    onValueChange = onBodyChange,
                    placeHolder = "Start Typing",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .constrainAs(bodyField) {
                        top.linkTo(titleField.bottom,8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                Button(
                    onClick = { onSaveClicked(context) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(btnSave) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom,8.dp)
                        }) {
                    Text(
                        text = "Save Note",
                        fontSize = 25.sp
                    )
                }
            }
        }
    }
}