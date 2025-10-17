package com.huertohogar.login

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

import com.huertohogar.ui.login.LoginViewModel
import com.huertohogar.ui.theme.BlancoSuave
import com.huertohogar.ui.theme.HuertohogarTheme
import com.huertohogar.ui.theme.TextoPrincipal


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LoginScreen(
    vm: LoginViewModel= viewModel()
){
    val satate = vm.uiState
    var showPass by remember { mutableStateOf(false) }

    //Colores
    HuertohogarTheme(useAlternativo = true){

        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "HuertoHogar",
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