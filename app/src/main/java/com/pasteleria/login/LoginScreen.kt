package com.pasteleria.login

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pasteleria.R
import com.pasteleria.ui.theme.BlancoSuave
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.rememberNavController
import com.pasteleria.ui.login.LoginViewModel

@Composable
fun BakeryLoginScreen(
    navController: NavController,
    vm: LoginViewModel = viewModel()
) {

    val primaryBrown = Color(0xFF6D4C41)
    val lightPinkBackground = Color(0xFFFCE4EC)
    val hintTextColor = Color(0xFF8D6E63)


    // Creamos un ColorScheme personalizado para usar nuestros colores.
    val BakeryColorScheme = lightColorScheme(
        primary = primaryBrown,            // Color principal (botones, texto destacado)
        onPrimary = Color.White,           // Color del texto sobre el primary
        background = lightPinkBackground,  // Color de fondo de la pantalla
        onBackground = primaryBrown,       // Color del texto sobre el fondo
        surface = Color.White,             // Fondo de componentes
        onSurface = primaryBrown,          // Color del texto sobre surface

        outline = hintTextColor
    )

    // Aplicamos el tema a toda la pantalla
    val defaultTypography = Typography
    MaterialTheme(
        colorScheme = BakeryColorScheme// <-- Ahora sí funciona
    ) {
        // --- ESTADO ---
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var showPassword by remember { mutableStateOf(false) }

        // --- LAYOUT con Surface para el fondo ---
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background // Usa el color de fondo definido en nuestro tema
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 64.dp)
                    .background(
                        color = BlancoSuave,
                        shape = RoundedCornerShape(16.dp) // <-- Aquí está el "border-radius"
                    )
                ,
                // Más padding horizontal
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "¡Dulce Bienvenida!",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- Icono de Cupcake ---
                // Necesitarás un recurso de imagen en res/drawable
                Image(
                    painter = painterResource(id = R.drawable.cupcake), // Reemplaza con tu drawable
                    contentDescription = "Cupcake Icon",
                    modifier = Modifier.size(120.dp) // Tamaño del icono
                )

                Spacer(modifier = Modifier.height(48.dp))

                // --- Campo de Texto para Email/Usuario ---
                OutlinedTextField(
                    value = vm.uiState.username,
                    onValueChange = vm::onUsernameChange,
                    label = {
                        Text(
                            "Usuario",
                            color = MaterialTheme.colorScheme.outline // Usa el color hint
                        )
                    },
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

                // --- Campo de Texto para Contraseña ---
                OutlinedTextField(
                    value = vm.uiState.password,
                    onValueChange = vm::onPasswordChange,
                    label = {
                        Text(
                            "Contraseña",
                            color = MaterialTheme.colorScheme.outline // Usa el color hint
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (showPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle password visibility",
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
                    )
                )

                Spacer(modifier = Modifier.height(48.dp))

                // --- Botón de Login ---
                Button(
                    onClick = {
                        vm.submit { username ->
                            navController.navigate("home/$username"){
                                popUpTo("login"){ inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary // Usa el marrón oscuro
                    )
                ) {
                    Text(
                        "Ingresar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary // El texto será blanco
                    )

                }
            }
        }
    }
}

// --- PREVIEW ---
@Preview(showBackground = true) // showSystemUi ya no da error si lo eliminas
@Composable
fun BakeryLoginScreenPreview() {
    val navController = rememberNavController()
    val vm = LoginViewModel()

    BakeryLoginScreen(navController = navController, vm = vm)
}
