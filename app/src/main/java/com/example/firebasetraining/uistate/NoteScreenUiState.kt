package com.example.firebasetraining.uistate

data class NoteScreenUiState(
    val title : String? = "",
    val body : String? = "",
    val isEditClicked : Boolean? = false,
    val isDeleteCLicked : Boolean = false,
    val isDoneClicked : Boolean = false,
    val showEdit : Boolean = true,
    val showDel : Boolean = true,
    val showDone : Boolean = false,
) {

}