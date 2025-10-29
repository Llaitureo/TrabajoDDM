package com.pasteleria.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pasteleria.login.BakeryLoginScreen
import com.pasteleria.ui.register.BakeryRegisterScreen
import com.pasteleria.ui.home.HomeScreem
import com.pasteleria.ui.pedido.PedidoScreen


@Composable
fun AppNav(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login"){
        composable(route= "login"){
            BakeryLoginScreen(navController)
        }

        composable(route = "register") {
            BakeryRegisterScreen(navController = navController)
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
            HomeScreem(username = username, navController = navController)
        }

        composable(
            // La nueva ruta con 3 argumentos
            route = "PedidoScreen/{nombre}/{precio}/{imagenResId}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("precio") { type = NavType.StringType },
                navArgument("imagenResId") { type = NavType.IntType } // Nuevo argumento
            )
        ) { backStackEntry ->
            // Leemos los argumentos
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val precio = backStackEntry.arguments?.getString("precio") ?: ""
            val imagenResId = backStackEntry.arguments?.getInt("imagenResId") ?: 0

            // Llamamos a la nueva pantalla
            PedidoScreen(
                navController = navController,
                nombre = nombre,
                precio = precio,
                imagenResId = imagenResId
            )
        }
    }
}