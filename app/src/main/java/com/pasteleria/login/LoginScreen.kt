package com.pasteleria.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.pasteleria.ui.login.LoginViewModel
import com.pasteleria.ui.theme.BlancoSuave
import com.pasteleria.ui.theme.HuertohogarTheme
import com.pasteleria.ui.theme.TextoPrincipal


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LoginScreen(
    vm: LoginViewModel= viewModel()
){
    val state = vm.uiState
    var showPass by remember { mutableStateOf(false) }

    //Colores
    HuertohogarTheme(useAlternativo = true){

        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Pastelería",
                    color = TextoPrincipal) })
            }
        ){
            innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(16.dp)
                        .background(BlancoSuave),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                }
        }
    }

}