package com.example.firebasetraining.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.firebasetraining.uistate.SignInScreenUiState
import com.example.firebasetraining.viewmodels.SignInViewModel

@Composable
fun SignInScreen(
    viewModel : SignInViewModel = hiltViewModel(),
    navController: NavController){
    val state = viewModel.state.collectAsState()
    SignInScreenContent(
        state = state,
        navController = navController,
        onEmailChange = viewModel::onEmailChange,
        onPassChange = viewModel::onPassChange,
        onShowPassClicked = viewModel::onShowPassClicked,
        onSignInClicked = viewModel::onSignInCLicked,
        onSignUpClicked = viewModel::onSignUpClicked
    )
}

@Composable
fun SignInScreenContent(
    state : State<SignInScreenUiState>,
    navController: NavController,
    onEmailChange:(String)->Unit,
    onPassChange:(String)->Unit,
    onShowPassClicked:()->Unit,
    onSignInClicked:(NavController, Context)->Unit,
    onSignUpClicked:(NavController)->Unit,
) {
    val context = LocalContext.current
    val showPassImage = if (state.value.isPassVisible)
        painterResource(R.drawable.show)
    else painterResource(R.drawable.hide)
    FireBaseTrainingTheme {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val (etEmail,etPass,
                btnSign,signInText,
                checkAcc,btnSignup,
                incorrect) = createRefs()
            Text(
                text = "Sign In to Notes",
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White,
                modifier = Modifier.constrainAs(signInText){
                    bottom.linkTo(etEmail.top,20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
         MyEmailEditText(
             value = state.value.email,
             onValueChange = onEmailChange,
             isError = state.value.isEmailError,
             modifier = Modifier
                 .constrainAs(etEmail){
                 top.linkTo(parent.top,200.dp)
                 start.linkTo(parent.start,15.dp)
                 end.linkTo(parent.end,15.dp)
             }
         )
            MyPasswordEditText(
                value = state.value.pass,
                onValueChange = onPassChange,
                iseError = state.value.isPassError,
                visualTransformation = if (state.value.isPassVisible)
                VisualTransformation.None
                else PasswordVisualTransformation(),
                onShowPassClicked = onShowPassClicked,
                label_placeholder = "Password",
                painter = showPassImage,
                modifier = Modifier .constrainAs(etPass){
                    top.linkTo(etEmail.bottom,20.dp)
                    start.linkTo(parent.start,15.dp)
                    end.linkTo(parent.end,15.dp)
                }
            )
            if (state.value.errorText.isNotEmpty()){
                Text(
                    text = state.value.errorText,
                    color = Color.Red,
                    fontSize = 18.sp,
                    modifier = Modifier.constrainAs(incorrect){
                        top.linkTo(etPass.bottom,8.dp)
                        start.linkTo(etPass.start,5.dp)
                    }
                )
            }
            MyButton(
                navController = navController,
                context = context,
                onClick = onSignInClicked,
                text = "Sign In",
                modifier = Modifier.constrainAs(btnSign){
                    top.linkTo(etPass.bottom,40.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            Text(
                text = "Don't have an Account?",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(checkAcc){
                    top.linkTo(btnSign.bottom,15.dp)
                    start.linkTo(parent.start,10.dp)
                })
            TextButton(
                onClick = {onSignUpClicked(navController)},
                modifier = Modifier.constrainAs(btnSignup){
                    top.linkTo(checkAcc.top)
                    bottom.linkTo(checkAcc.bottom)
                    start.linkTo(checkAcc.end,8.dp)
                }) {
                Text(
                    text = "Sign Up",
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
