package com.pasteleria.ui.register // O la ruta que hayas elegido, por ejemplo com.pasteleria.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.* // Necesario para LaunchedEffect, remember, mutableStateOf, etc.
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // Importa viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pasteleria.R // Asegúrate que esta importación sea correcta para tus recursos (R.drawable.cupcake)
import com.pasteleria.ui.theme.BlancoSuave // Importa tu color personalizado
// Asegúrate de que la ruta a RegisterViewModel es correcta
import com.pasteleria.ui.register.RegisterViewModel // Importa tu ViewModel

@OptIn(ExperimentalMaterial3Api::class) // Necesario para Scaffold, TopAppBar, OutlinedTextField
@Composable
fun BakeryRegisterScreen(
    navController: NavController,
    vm: RegisterViewModel = viewModel() // <-- Inyecta el ViewModel
) {
    val uiState = vm.uiState // Obtiene el estado del ViewModel

    // --- Colores (iguales a LoginScreen) ---
    val primaryBrown = Color(0xFF6D4C41)
    val lightPinkBackground = Color(0xFFFCE4EC)
    val hintTextColor = Color(0xFF8D6E63)

    // --- ColorScheme (igual a LoginScreen) ---
    val BakeryColorScheme = lightColorScheme(
        primary = primaryBrown,
        onPrimary = Color.White,
        background = lightPinkBackground,
        onBackground = primaryBrown,
        surface = Color.White,
        onSurface = primaryBrown,
        outline = hintTextColor,
        secondary = hintTextColor, // Color para el botón Cancelar
        onSecondary = Color.White,
        error = Color.Red // Color para mensajes de error
    )

    // Efecto para navegar cuando el registro es exitoso
    LaunchedEffect(uiState.registrationSuccess) {
        if (uiState.registrationSuccess) {
            // Navega directamente al login
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
                launchSingleTop = true
            }
            vm.registrationHandled() // Resetea el estado en el ViewModel
        }
    }

    MaterialTheme(colorScheme = BakeryColorScheme) {
        // --- Estado local para mostrar/ocultar contraseñas ---
        var showPassword by remember { mutableStateOf(false) }
        var showConfirmPassword by remember { mutableStateOf(false) }

        // Usamos Surface para el fondo general, Column para el contenido centrado
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background // Fondo rosa pálido
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 32.dp), // Menos padding vertical para que quepa todo
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Contenedor blanco redondeado
                Column(
                    modifier = Modifier
                        //.fillMaxSize() // Quita fillMaxSize aquí para que se ajuste al contenido
                        .background(
                            color = BlancoSuave, // Color blanco suave
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 24.dp, vertical = 32.dp), // Padding interno del contenedor blanco
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    Text(
                        text = "Crea tu Cuenta",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Image(
                        painter = painterResource(id = R.drawable.cupcake), // Imagen del cupcake
                        contentDescription = "Icono Registro",
                        modifier = Modifier.size(80.dp) // Más pequeño
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- Campo Usuario ---
                    OutlinedTextField(
                        value = uiState.username,
                        onValueChange = vm::onUsernameChange,
                        label = { Text("Nuevo Usuario", color = MaterialTheme.colorScheme.outline) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = hintTextColor,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // --- Campo Contraseña ---
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
                                    contentDescription = "Mostrar/Ocultar contraseña",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = hintTextColor,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        ),
                        isError = uiState.error == "Las contraseñas no coinciden"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // --- Campo Confirmar Contraseña ---
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
                                    contentDescription = "Mostrar/Ocultar confirmación",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = hintTextColor,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        ),
                        isError = uiState.error == "Las contraseñas no coinciden"
                    )

                    // Mensaje de error
                    if (uiState.error != null) {
                        Text(
                            uiState.error!!,
                            color = MaterialTheme.colorScheme.error, // Usará el color Rojo definido en el ColorScheme
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp).align(Alignment.Start) // Alinea a la izquierda
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp)) // Espacio antes de los botones

                    // --- Botones ---
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre botones
                    ) {
                        // --- Botón Cancelar ---
                        Button(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.weight(1f).height(48.dp), // Botones un poco más bajos
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text(
                                "Cancelar",
                                fontSize = 16.sp, // Texto un poco más pequeño
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }

                        // --- Botón Registrar ---
                        Button(
                            onClick = { vm.registerUser() },
                            modifier = Modifier.weight(1f).height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            //
                            enabled = uiState.username.isNotBlank() &&
                                    uiState.password.isNotBlank() &&
                                    uiState.confirmPassword.isNotBlank() &&
                                    //uiState.error == null && // Permitir registrar aunque haya otro error
                                    uiState.password == uiState.confirmPassword && // Solo valida coincidencia aquí
                                    !uiState.isLoading
                        ) {
                            Text(
                                if (uiState.isLoading) "Registrando..." else "Registrar",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    } // Fin Row Botones
                } // Fin Column Contenedor Blanco
            } // Fin Column Principal
        } // Fin Surface
    } // Fin MaterialTheme
}

// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun BakeryRegisterScreenPreview() {
    val navController = rememberNavController()
    // La preview usará un ViewModel temporal, no interactuará con la BD real
    BakeryRegisterScreen(navController = navController)
}