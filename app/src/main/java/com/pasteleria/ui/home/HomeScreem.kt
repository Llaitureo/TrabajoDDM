package com.pasteleria.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pasteleria.ui.theme.HuertohogarTheme

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HomeScreem(
    username : String,
    navController: NavController
){
    HuertohogarTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {Text(text = "Bienvenodo")}
                )
            }
        ){innerpadin ->
            Column (
                modifier = Modifier
                    .padding(innerpadin)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "hola $username",
                    style = MaterialTheme.typography.headlineMedium
                )

                Button(
                    onClick = {
                        navController.navigate("login"){
                            popUpTo("login"){inclusive = true}
                            launchSingleTop=true
                        }
                    }
                ) {
                    Text("cerrar sesion")
                }

            }
        }
    }
}