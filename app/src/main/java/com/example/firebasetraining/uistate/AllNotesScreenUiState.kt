package com.example.firebasetraining.uistate

import com.example.firebasetraining.Note

data class AllNotesScreenUiState(
    val noteTitle : String = "",
    val noteBody : String = "" ,
    val showDialog : Boolean = false,
    val notesList : List<Note> = emptyList()
)
