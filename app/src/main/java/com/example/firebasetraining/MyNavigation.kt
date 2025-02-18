package com.example.firebasetraining

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firebasetraining.screens.MainScreen
import com.example.firebasetraining.screens.NoteScreen
import com.example.firebasetraining.screens.SignInScreen
import com.example.firebasetraining.screens.SignUpScreen
import com.example.firebasetraining.screens.SplashScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.SplashScreen.route){ SplashScreen(navController = navController) }
        composable(route = Screens.MainScreen.route){ MainScreen(navController = navController) }
        composable(route = Screens.SignInScreen.route){ SignInScreen(navController = navController) }
        composable(route = Screens.SignUpScreen.route){ SignUpScreen(navController = navController) }
        composable(route = Screens.NoteScreen.route,
            arguments = listOf(navArgument("data"){
                type= NavType.StringType
            })){ navabakstackEntry->
                val data =navabakstackEntry.arguments?.getString("data")?:"No Data"
                NoteScreen(data = data, navController = navController) }
    }
}
sealed class Screens(val route:String){
    object SplashScreen : Screens(route = "SplashScreen")
     object MainScreen : Screens("MainScreen")
    object NoteScreen : Screens(route = "NoteScreen/{data}")
    object SignInScreen : Screens(route = "SingInScreen")
    object SignUpScreen : Screens(route = "SingUpScreen")
    fun createRoute(data : String) :String ="NoteScreen/$data"
}