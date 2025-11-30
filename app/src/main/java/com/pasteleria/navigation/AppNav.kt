package com.pasteleria.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pasteleria.login.BakeryLoginScreen
import com.pasteleria.ui.boleta.BoletaScreen
import com.pasteleria.ui.boleta.BoletaViewModel
import com.pasteleria.ui.historial.HistorialScreen
import com.pasteleria.ui.register.BakeryRegisterScreen
import com.pasteleria.ui.home.HomeScreem
import com.pasteleria.ui.pedido.PedidoScreen
import com.pasteleria.ui.perfil.ProfileScreen
import com.pasteleria.ui.perfil.ProfileViewModel
import com.pasteleria.ui.qr.QrScannerScreen
import com.pasteleria.ui.qr.QrViewModel


@Composable
fun AppNav(){
    val navController = rememberNavController()

    val boletaViewModel: BoletaViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val qrViewModel: QrViewModel = viewModel()

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
            HomeScreem(username = username, navController = navController, boletaViewModel = boletaViewModel)

            LaunchedEffect(username) {
                boletaViewModel.setUsuarioActual(username)
            }

            HomeScreem(
                username = username,
                navController = navController,
                boletaViewModel = boletaViewModel
            )
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
            route = "profile/{username}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            ProfileScreen(
                navController = navController,
                profileViewModel = profileViewModel,
                username = username
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
                boletaViewModel = boletaViewModel
            )
        }

        composable(
            route = "qr_scanner/{username}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            QrScannerScreen(
                viewModel = qrViewModel,
                onNavigateBack = {
                    navController.navigate("home/$username") {
                        popUpTo("home/$username") { inclusive = false }
                    }
                }
            )
        }

        composable(route = "map") {
            com.pasteleria.ui.map.MapScreen(navController = navController)
        }

        composable(
            route = "historial/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            HistorialScreen(navController = navController, username = username)
        }
    }
}