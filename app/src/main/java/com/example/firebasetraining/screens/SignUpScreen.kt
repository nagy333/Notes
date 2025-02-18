package com.example.firebasetraining.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.firebasetraining.R
import com.example.firebasetraining.composables.MyButton
import com.example.firebasetraining.composables.MyEmailEditText
import com.example.firebasetraining.composables.MyPasswordEditText
import com.example.firebasetraining.ui.theme.FireBaseTrainingTheme
import com.example.firebasetraining.uistate.SignUpScreenUiState
import com.example.firebasetraining.viewmodels.SignUpViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController){
    val state = viewModel.state.collectAsState()
    FireBaseTrainingTheme {
        SignUpScreenContent(
            navController = navController,
            state = state,
            onEmailChange = viewModel::onEmailChange,
            onPassChange = viewModel::onPassChange,
            onConPassChange = viewModel::onConfPassChange,
            onSignUpClicked = viewModel::onSignUpCLicked,
            onSignInClicked = viewModel::onSignInClicked,
            onShowPassClicked = viewModel::onShowPassClicked,
            onShowConfPassClicked = viewModel::onShowConfPassClicked,

            )
    }

}

@Composable
fun SignUpScreenContent(
    navController: NavController,
    state : State<SignUpScreenUiState>,
    onEmailChange:(String)->Unit,
    onPassChange:(String)->Unit,
    onConPassChange:(String)->Unit,
    onSignUpClicked:(NavController,Context)->Unit,
    onSignInClicked:(NavController)->Unit,
    onShowPassClicked:()->Unit,
    onShowConfPassClicked:()->Unit,
) {
    FireBaseTrainingTheme {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val context = LocalContext.current
            val (etEmail,etPass,etConfPass,
                btnSign,checkAcc,btnSignIn,
                match,signupText) = createRefs()
            val passImage = if (state.value.isPassVisible)
                painterResource(R.drawable.show)
            else painterResource(R.drawable.hide)
            val confPassImage = if (state.value.isConfPassVisible)
                painterResource(R.drawable.show)
            else painterResource(R.drawable.hide)
            val matchText = if (state.value.isPassMatch)"" else "Password doesn't match"

            Text(
                text = "Sign up to Notes",
                color = Color.White,
                fontSize = 30.sp,
                modifier = Modifier.constrainAs(signupText){
                    start.linkTo(parent.start,5.dp)
                    end.linkTo(parent.end,5.dp)
                    bottom.linkTo(etEmail.top,20.dp)
                }
            )
            MyEmailEditText(
                value = state.value.email,
                onValueChange = onEmailChange,
                isError = state.value.emailErrorText.isNotEmpty(),
                modifier = Modifier .constrainAs(etEmail) {
                    top.linkTo(parent.top, 200.dp)
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                })
            MyPasswordEditText(
                value = state.value.pass,
                onValueChange = onPassChange,
                iseError = state.value.passErrorText.isNotEmpty(),
                label_placeholder = "Password",
                visualTransformation = if (state.value.isPassVisible)
                    VisualTransformation.None
                else PasswordVisualTransformation(),
                onShowPassClicked = onShowPassClicked,
                painter = passImage,
                modifier = Modifier.constrainAs(etPass){
                    top.linkTo(etEmail.bottom,8.dp)
                    start.linkTo(parent.start,8.dp)
                    end.linkTo(parent.end,8.dp)
                }
            )
            MyPasswordEditText(
                value = state.value.confPass,
                onValueChange = onConPassChange,
                iseError = state.value.confPassErrorText.isNotEmpty(),
                visualTransformation = if (state.value.isConfPassVisible)
                    VisualTransformation.None
                else PasswordVisualTransformation(),
                onShowPassClicked = onShowConfPassClicked,
                painter = confPassImage,
                label_placeholder = "Confirm Password",
                modifier = Modifier.constrainAs(etConfPass) {
                    top.linkTo(etPass.bottom, 20.dp)
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                }
            )
            Text(
                text = matchText,
                color = Color.Red,
                fontSize = 15.sp,
                modifier = Modifier.constrainAs(match){
                    start.linkTo(etConfPass.start)
                    top.linkTo(etConfPass.bottom,4.dp)
                })
           MyButton(
               navController = navController,
               context = context,
               onClick = onSignUpClicked,
               text = "Sign Up",
               modifier = Modifier .constrainAs(btnSign) {
                   top.linkTo(match.bottom, 20.dp)
                   start.linkTo(parent.start)
                   end.linkTo(parent.end)
               }
           )
            Text(
                text = "Already Have An Account?",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(checkAcc){
                    top.linkTo(btnSign.bottom,15.dp)
                    start.linkTo(parent.start,10.dp)
                })
            TextButton(
                onClick = {onSignInClicked(navController)},
                modifier = Modifier.constrainAs(btnSignIn){
                    top.linkTo(checkAcc.top)
                    bottom.linkTo(checkAcc.bottom)
                    start.linkTo(checkAcc.end,8.dp)
                }) {
                Text(
                    text = "Sign In",
                    fontSize = 20.sp,
                    color = Color.White)
                Icon(
                   imageVector =  Icons.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 4.dp)
                    )
            }
        }
    }


}
