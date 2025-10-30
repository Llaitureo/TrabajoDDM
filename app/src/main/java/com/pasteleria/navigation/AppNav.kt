package com.pasteleria.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pasteleria.login.BakeryLoginScreen
import com.pasteleria.ui.boleta.BoletaScreen
import com.pasteleria.ui.boleta.BoletaViewModel
import com.pasteleria.ui.register.BakeryRegisterScreen
import com.pasteleria.ui.home.HomeScreem
import com.pasteleria.ui.pedido.PedidoScreen


@Composable
fun AppNav(){
    val navController = rememberNavController()
    val boletaViewModel: BoletaViewModel = viewModel()//Importante poner :(

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
            route="pedidosScreen/{nombre}/{precio}/{imagenResId}",
        ){
            backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?:""
            val precio = backStackEntry.arguments?.getInt("precio") ?: 0
            val imagenResId = backStackEntry.arguments?.getInt("imagenResId") ?: 0

            PedidoScreen(
                navController = navController,
                nombre = nombre,
                precio = precio,
                imagenResId = imagenResId,
                boletaViewModel = boletaViewModel
            )
        }

        composable(route = "boleta"){
            BoletaScreen(
                navController = navController,
                boletaViewModel = boletaViewModel
            )
        }

        composable(
            route = "PedidoScreen/{nombre}/{precio}/{imagenResId}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("precio") { type = NavType.IntType },
                navArgument("imagenResId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val precio = backStackEntry.arguments?.getInt("precio") ?: 0
            val imagenResId = backStackEntry.arguments?.getInt("imagenResId") ?: 0

            PedidoScreen(
                navController = navController,
                nombre = nombre,
                precio = precio,
                imagenResId = imagenResId,
                boletaViewModel = boletaViewModel//Esto añade a la lista creada.
            )
        }
    }
}