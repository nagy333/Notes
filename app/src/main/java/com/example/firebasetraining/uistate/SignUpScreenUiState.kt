package com.example.firebasetraining.uistate

data class SignUpScreenUiState(
    val email : String = "",
    val pass : String = "",
    val confPass : String = "",
    val isSignUpCLicked : Boolean = false,
    val isPassMatch : Boolean = true,
    val isPassVisible : Boolean = false,
    val isConfPassVisible : Boolean = false,
    val emailErrorText : String = "",
    val passErrorText : String = "",
    val confPassErrorText : String = "",
)
