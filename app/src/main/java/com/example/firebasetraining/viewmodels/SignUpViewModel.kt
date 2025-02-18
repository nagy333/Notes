package com.example.firebasetraining.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.firebasetraining.NotesRepo
import com.example.firebasetraining.Screens
import com.example.firebasetraining.uistate.SignUpScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel@Inject constructor(
    private val repo: NotesRepo
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpScreenUiState())
    val state : StateFlow<SignUpScreenUiState> get() = _state
    fun onEmailChange(value : String){
        _state.update { it.copy(email = value) }
    }
    fun onPassChange(value : String){
        _state.update { it.copy(pass = value) }
    }
    fun onConfPassChange(value : String){
        _state.update { it.copy(confPass = value) }
    }
    fun onSignUpCLicked(
        navController: NavController,
        context: Context){
        if (_state.value.pass!=_state.value.confPass){
            _state.update { it.copy(isPassMatch = false) }
        }
        if (
            (_state.value.email.contains("@")&&_state.value.email.isNotEmpty())
            &&_state.value.pass.isNotEmpty()){
            repo.signUp(
                email = _state.value.email,
                pass = _state.value.pass).addOnCompleteListener{
                    Toast.makeText(
                        context,
                        "User Registered Successfully,Pleas Sign in",
                        Toast.LENGTH_LONG).show()
                navController.navigate(Screens.SignInScreen.route)
            }
        }
        var emailErr = ""
        emailErr = if (_state.value.email.isEmpty()) "Email Cannot be Empty" else ""
        emailErr = if (!_state.value.email.contains("@")&&_state.value.email.isNotEmpty())
            "Email must contain '@'" else ""

       val passErr =
            if (_state.value.pass.length<8)
                "Password must be at least 8 characters long" else ""
       val confPassErr = if (_state.value.pass!=_state.value.confPass) "Passwords doesn't Match" else ""
        _state.update { it.copy(
            emailErrorText = emailErr,
            passErrorText = passErr,
            confPassErrorText = confPassErr) }

    }
    fun onSignInClicked(navController: NavController){
        navController.navigate(Screens.SignInScreen.route)
    }
    fun onShowPassClicked(){
        _state.update { it.copy(isPassVisible = !_state.value.isPassVisible) }
    }
    fun onShowConfPassClicked(){
        _state.update { it.copy(isConfPassVisible = !_state.value.isConfPassVisible) }
    }
}