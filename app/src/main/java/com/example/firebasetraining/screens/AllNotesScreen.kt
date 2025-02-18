package com.example.firebasetraining.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.firebasetraining.Note
import com.example.firebasetraining.Screens
import com.example.firebasetraining.composables.MyAlertDialog
import com.example.firebasetraining.composables.MyTopAppBar
import com.example.firebasetraining.uistate.AllNotesScreenUiState
import com.example.firebasetraining.viewmodels.AllNoteScreenViewModel


@Composable
fun MainScreen(
    viewModel: AllNoteScreenViewModel = hiltViewModel(),
    navController: NavController){
    val state = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
          MyTopAppBar(
              title = "All Notes",
              onSingOutClicked = { viewModel.onSignOutClicked(navController) }
          )
        },
        floatingActionButton ={
            FloatingActionButton(
                onClick = viewModel::onFabClicked,
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = Color.White,
                shape = CircleShape,){
                    Icon(Icons.Filled.Add, contentDescription = "")
                }


        }
    ) {paddingValues ->
        MainScreenContent(
            state = state,
            onTitleChange = viewModel::onTitleValueChange,
            onBodyChange = viewModel::onBodyValueChange,
            onDismissRequest = viewModel::onDismissRequest,
            onSaveClicked = viewModel::onBtnSaveClicked,
            navController = navController,
            padding = paddingValues)
    }

}
@Composable
fun MainScreenContent(
    state : State<AllNotesScreenUiState>,
    navController: NavController,
    onTitleChange:(String)->Unit,
    onBodyChange:(String)->Unit,
    onDismissRequest:()->Unit,
    onSaveClicked:(Context)->Unit,
    padding : PaddingValues){
    val context = LocalContext.current
    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(padding)){
        val (dialog,list) = createRefs()
        if (state.value.showDialog) {
           MyAlertDialog(
               context = context,
               noteTitle = state.value.noteTitle,
               onTitleChange = onTitleChange,
               noteBody = state.value.noteBody,
               onBodyChange = onBodyChange,
               onSaveClicked = onSaveClicked,
               onDismissRequest = onDismissRequest,
               modifier = Modifier.constrainAs(dialog) {
                   start.linkTo(parent.start, 4.dp)
                   end.linkTo(parent.end, 4.dp)
                   top.linkTo(parent.top)
                   bottom.linkTo(parent.bottom)
                   })
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .constrainAs(list) {
                    top.linkTo(parent.top,10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
            items(state.value.notesList){
                NoteItem(it,navController)
            }
        }
    }

    }

@Composable
fun NoteItem(
    note : Note,
    navController: NavController){
    Card(
        onClick = {
            navController.navigate(Screens.NoteScreen.createRoute(note.id!!))
                  },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,),
        border = BorderStroke(2.dp,MaterialTheme.colorScheme.background)
    ) {
        ConstraintLayout (
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                ) {
            val (noteTitle,date) = createRefs()
            Text(
                text = note.noteTitle!!,
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier
                    .constrainAs(noteTitle){
                        top.linkTo(parent.top,15.dp)
                        start.linkTo(parent.start,10.dp)
                    })
            Text(
                text = note.date!!,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .constrainAs(date){
                        bottom.linkTo(parent.bottom,15.dp)
                        end.linkTo(parent.end,10.dp)
                    })
        }
    }

}