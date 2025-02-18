package com.example.firebasetraining.uistate

data class SignInScreenUiState(
    val email : String = "",
    val pass : String = "",
    val isSignInClicked : Boolean = false,
    val isPassVisible : Boolean = false,
    val isEmailError : Boolean = false,
    val isPassError : Boolean = false,
    val errorText : String = ""
) {
}