package com.pasteleria.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pasteleria.R
import com.pasteleria.ui.theme.BlancoSuave

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BakeryRegisterScreen(
    navController: NavController,
    vm: RegisterViewModel = viewModel()
) {
    val uiState = vm.uiState

    val primaryBrown = Color(0xFF6D4C41)
    val lightPinkBackground = Color(0xFFFCE4EC)
    val hintTextColor = Color(0xFF8D6E63)

    val BakeryColorScheme = lightColorScheme(
        primary = primaryBrown,
        onPrimary = Color.White,
        background = lightPinkBackground,
        onBackground = primaryBrown,
        surface = Color.White,
        onSurface = primaryBrown,
        outline = hintTextColor,
        secondary = hintTextColor,
        onSecondary = Color.White,
        error = Color.Red
    )

    LaunchedEffect(uiState.registrationSuccess) {
        if (uiState.registrationSuccess) {
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
                launchSingleTop = true
            }
            vm.registrationHandled()
        }
    }

    MaterialTheme(colorScheme = BakeryColorScheme) {
        var showPassword by remember { mutableStateOf(false) }
        var showConfirmPassword by remember { mutableStateOf(false) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            color = BlancoSuave,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Crea tu Cuenta",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Image(
                        painter = painterResource(id = R.drawable.cupcake),
                        contentDescription = "Icono Registro",
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = uiState.username,
                        onValueChange = vm::onUsernameChange,
                        label = { Text("Nuevo Usuario", color = MaterialTheme.colorScheme.outline) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = vm::onPasswordChange,
                        label = { Text("Nueva Contraseña", color = MaterialTheme.colorScheme.outline) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Mostrar/Ocultar contraseña"
                                )
                            }
                        },
                        isError = uiState.error == "Las contraseñas no coinciden"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = uiState.confirmPassword,
                        onValueChange = vm::onConfirmPasswordChange,
                        label = { Text("Confirmar Contraseña", color = MaterialTheme.colorScheme.outline) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                                Icon(
                                    imageVector = if (showConfirmPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Mostrar/Ocultar confirmación"
                                )
                            }
                        },
                        isError = uiState.error == "Las contraseñas no coinciden"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo Fecha de Nacimiento
                    OutlinedTextField(
                        value = uiState.birthDate,
                        onValueChange = vm::onBirthDateChange,
                        label = { Text("Fecha de Nacimiento (DD/MM/AAAA)", color = MaterialTheme.colorScheme.outline) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo Código de Descuento
                    OutlinedTextField(
                        value = uiState.discountCode,
                        onValueChange = vm::onDiscountCodeChange,
                        label = { Text("Código de Descuento (opcional)", color = MaterialTheme.colorScheme.outline) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (uiState.error != null) {
                        Text(
                            uiState.error!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp).align(Alignment.Start)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.weight(1f).height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Text("Cancelar", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSecondary)
                        }

                        Button(
                            onClick = { vm.registerUser() },
                            modifier = Modifier.weight(1f).height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            enabled = uiState.username.isNotBlank() &&
                                    uiState.password.isNotBlank() &&
                                    uiState.confirmPassword.isNotBlank() &&
                                    uiState.password == uiState.confirmPassword &&
                                    !uiState.isLoading
                        ) {
                            Text(
                                if (uiState.isLoading) "Registrando..." else "Registrar",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BakeryRegisterScreenPreview() {
    val navController = rememberNavController()
    BakeryRegisterScreen(navController = navController)
}