package com.pasteleria.register

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pasteleria.R
import com.pasteleria.ui.theme.BlancoSuave // Asegúrate que esta importación sea correcta

@Composable
fun BakeryRegisterScreen(
    navController: NavController
    // Podrías añadir un ViewModel aquí si quieres lógica de registro real
) {

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
        // Añadimos un color secundario para el botón Cancelar
        secondary = hintTextColor, // Puedes usar otro color si prefieres
        onSecondary = Color.White
    )

    MaterialTheme(
        colorScheme = BakeryColorScheme
    ) {
        // --- Estado local para los campos ---
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var showPassword by remember { mutableStateOf(false) }
        var showConfirmPassword by remember { mutableStateOf(false) }
        var passwordsMatch by remember { mutableStateOf(true) } // Para validar si coinciden

        // --- Layout ---
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 64.dp)
                    .background(
                        color = BlancoSuave, // Usando tu color BlancoSuave
                        shape = RoundedCornerShape(16.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Crea tu Cuenta",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- Icono ---
                Image(
                    painter = painterResource(id = R.drawable.cupcake), // Reutilizamos el cupcake
                    contentDescription = "Icono Registro",
                    modifier = Modifier.size(100.dp) // Un poco más pequeño
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- Campo Usuario ---
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Nuevo Usuario", color = MaterialTheme.colorScheme.outline) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors( // Estilo igual a Login
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
                    value = password,
                    onValueChange = {
                        password = it
                        // Validar si coinciden al cambiar la contraseña
                        passwordsMatch = password == confirmPassword
                    },
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
                    colors = OutlinedTextFieldDefaults.colors( // Estilo igual a Login
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = hintTextColor,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    // Marcar error si no coinciden
                    isError = !passwordsMatch
                )

                Spacer(modifier = Modifier.height(16.dp))

                // --- Campo Confirmar Contraseña ---
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        // Validar si coinciden al cambiar la confirmación
                        passwordsMatch = password == confirmPassword
                    },
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
                    colors = OutlinedTextFieldDefaults.colors( // Estilo igual a Login
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = hintTextColor,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    // Marcar error si no coinciden
                    isError = !passwordsMatch
                )

                // Mensaje de error si las contraseñas no coinciden
                if (!passwordsMatch) {
                    Text(
                        "Las contraseñas no coinciden",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }


                Spacer(modifier = Modifier.height(32.dp))

                // --- Botones ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly // Espacio entre botones
                ) {
                    // --- Botón Cancelar ---
                    Button(
                        onClick = {
                            // Vuelve a la pantalla anterior (Login)
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .weight(1f) // Ocupa la mitad del espacio
                            .height(56.dp)
                            .padding(end = 8.dp), // Espacio entre botones
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary // Color secundario
                        )
                    ) {
                        Text(
                            "Cancelar",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }

                    // --- Botón Registrar ---
                    Button(
                        onClick = {
                            // TODO: Añadir lógica de registro real (ViewModel)
                            // Por ahora, solo imprime y vuelve al login
                            println("Registrando Usuario: $username")
                            // Navega al login después de "registrar"
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier
                            .weight(1f) // Ocupa la mitad del espacio
                            .height(56.dp)
                            .padding(start = 8.dp), // Espacio entre botones
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary // Marrón
                        ),
                        // Habilitar solo si los campos no están vacíos Y las contraseñas coinciden
                        enabled = username.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank() && passwordsMatch
                    ) {
                        Text(
                            "Registrar",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                } // Fin Row Botones
            } // Fin Column
        } // Fin Surface
    } // Fin MaterialTheme
}

// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun BakeryRegisterScreenPreview() {
    val navController = rememberNavController()
    BakeryRegisterScreen(navController = navController)
}