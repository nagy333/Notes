package com.example.firebasetraining.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.collection.emptyLongSet
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.firebasetraining.NotesRepo
import com.example.firebasetraining.Screens
import com.example.firebasetraining.uistate.SignInScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: NotesRepo
) : ViewModel() {
    private val _state = MutableStateFlow(SignInScreenUiState())
    val state = _state.asStateFlow()
    fun onEmailChange(value: String) {
        _state.update { it.copy(email = value) }
    }

    fun onPassChange(value: String) {
        _state.update { it.copy(pass = value) }
    }

    fun onShowPassClicked() {
        _state.update { it.copy(isPassVisible = !_state.value.isPassVisible) }
    }

    fun onSignInCLicked(
        navController: NavController,
        context: Context
    ) {
        if (_state.value.pass.isNotEmpty() && _state.value.email.isNotEmpty()) {
            repo
                .signIn(email = state.value.email, pass = _state.value.pass)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        navController.navigate(Screens.MainScreen.route)
                    }
                    else{
                        _state.update {
                            it.copy(
                                isEmailError = true,
                                isPassError = true,
                                errorText = "Incorrect Email or Password"
                            )
                        }
                    }
                }
        } else {
            if (_state.value.pass.isEmpty() && _state.value.email.isEmpty()) {
                _state.update {
                    it.copy(
                        isEmailError = true,
                        isPassError = true,
                        errorText = "Email and Password Cannot be Empty"
                    )
                }


            } else if (_state.value.pass.isNotEmpty() && _state.value.email.isNotEmpty()) {
                _state.update {
                    it.copy(
                        isEmailError = false,
                        isPassError = false,
                        errorText = ""
                    )
                }
            }
            else if (_state.value.email.isEmpty()) {
                _state.update {
                    it.copy(
                        isEmailError = true,
                        errorText = "Email Cannot be Empty"
                    )
                }
            } else if (_state.value.email.isNotEmpty()) {
                _state.update {
                    it.copy(
                        isEmailError = false,
                        errorText = ""
                    )
                }
            }
            else if (_state.value.pass.isEmpty()) {
                _state.update {
                    it.copy(
                        isPassError = true,
                        errorText = "Password Cannot be Empty"
                    )
                }

            } else if (_state.value.pass.isNotEmpty()) {
                _state.update {
                    it.copy(
                        isPassError = false,
                        errorText = ""
                    )
                }
            }

        }

    }

    fun onSignUpClicked(navController: NavController) {
        navController.navigate(Screens.SignUpScreen.route)
    }

}