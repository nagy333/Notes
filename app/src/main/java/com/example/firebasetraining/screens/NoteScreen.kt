package com.example.firebasetraining.screens

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.firebasetraining.composables.MyEditText
import com.example.firebasetraining.uistate.NoteScreenUiState
import com.example.firebasetraining.viewmodels.NoteScreenViewModel

@Composable
fun NoteScreen(
    data : String,
    viewModel : NoteScreenViewModel = hiltViewModel(),
    navController: NavController){
    LaunchedEffect(data) {
        viewModel.getNoteById(data)
    }
    val state = viewModel.state.collectAsState()
    NoteScreenContent(
        state = state,
        navController = navController,
        noteId = data,
        onEditClick = viewModel::onEditClicked,
        onDeleteClick = viewModel::onDeleteClicked,
        onBodyChange = viewModel::onBodyChange,
        onTitleChange = viewModel::onTitleChange,
        onDoneClicked = viewModel::onDoneClicked)

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreenContent(
    state : State<NoteScreenUiState>,
    navController: NavController,
    noteId : String,
    onEditClick:()->Unit,
    onDeleteClick:(String,Context)->Unit,
    onTitleChange:(String)->Unit,
    onBodyChange:(String)->Unit,
    onDoneClicked:(String)->Unit){
    val context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Note",
                    fontSize = 30.sp,
                    color = Color.White)
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ), navigationIcon = { IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White)
            } },
            actions = {
            if (state.value.showEdit) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = null,
                        tint = Color.White)
                }
            }
            if (state.value.showDel) {
                IconButton(onClick = {
                    onDeleteClick(noteId,context)
                navController.navigateUp()}) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = null,
                        tint = Color.White)
                }
            }
            if (state.value.showDone) {
                IconButton(onClick = {onDoneClicked(noteId)}) {
                    Icon(
                        Icons.Filled.Done,
                        contentDescription = null,
                        tint = Color.White)
                }
            }
        })
    }) {innerpadding->
        ScreenContent(
            state = state,
            paddingValues = innerpadding,
            onBodyChange = onBodyChange,
            onTitleChange = onTitleChange)
    }

}
@Composable
fun ScreenContent(
    paddingValues: PaddingValues,
    state: State<NoteScreenUiState>,
    onBodyChange:(String)->Unit,
    onTitleChange:(String)->Unit,){
    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)){
        val (body,title) = createRefs()
        MyEditText(
            value = state.value.title!!,
            onValueChange = onTitleChange,
            readOnly = !state.value.isEditClicked!!,
            textStyle = TextStyle(fontSize = 30.sp),
            placeHolder = "",
            modifier = Modifier.constrainAs(title){
                top.linkTo(parent.top,16.dp)
                start.linkTo(parent.start,5.dp)
                end.linkTo(parent.end,5.dp)
            }
            )
        MyEditText(
            value = state.value.body!!,
            onValueChange = onBodyChange,
            readOnly = !state.value.isEditClicked!!,
            textStyle = TextStyle(fontSize = 25.sp),
            placeHolder = "",
            modifier = Modifier.constrainAs(body){
                top.linkTo(title.bottom,10.dp)
                start.linkTo(parent.start,5.dp)
                end.linkTo(parent.end,5.dp)
            })
    }
}