package com.pasteleria.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pasteleria.login.BakeryLoginScreen


@Composable
fun AppNav(){
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "Login"){
        composable(route= "login"){
            BakeryLoginScreen(navController)
        }
        composable(
            route = "home/{username}",
            arguments = listOf(
                navArgument("username"){
                    type = NavType.StringType
                }
            )
        ){
            backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
        }
    }
}