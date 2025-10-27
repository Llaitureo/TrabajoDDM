package com.pasteleria.ui.pedido

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pasteleria.ui.theme.HuertohogarTheme
import java.net.URLDecoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidoScreen(
    navController: NavController,
    nombre: String,
    precio: String,
    imagenResId: Int
) {
    // Variable para guardar la cantidad
    var cantidad by remember { mutableStateOf(1) }
    // Decodificamos el nombre por si tiene espacios (ej. "Pie de Limón")
    val nombreDecodificado = remember { URLDecoder.decode(nombre, "UTF-8") }

    HuertohogarTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Detalle del Producto") },
                    // Este es el botón para devolverse que pediste
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- Imagen Grande ---
                Image(
                    painter = painterResource(id = imagenResId),
                    contentDescription = nombreDecodificado,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(24.dp))

                // --- Título ---
                Text(
                    text = nombreDecodificado,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // --- Precio ---
                Text(
                    text = "$${precio}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- Selector de Cantidad ---
                QuantitySelector(
                    cantidad = cantidad,
                    onCantidadChange = { nuevaCantidad ->
                        if (nuevaCantidad >= 1) { // No permitir cantidad 0 o menor
                            cantidad = nuevaCantidad
                        }
                    }
                )

                Spacer(modifier = Modifier.weight(1f)) // Empuja el botón al fondo

                // --- Botón de Confirmar ---
                Button(
                    onClick = {
                        // TODO: Aquí irá la lógica para guardar el pedido
                        // Por ahora, solo vuelve a la pantalla anterior
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Confirmar Pedido", fontSize = 18.sp)
                }
            }
        }
    }
}

/**
 * Un Composable reutilizable para el selector de cantidad "- 1 +"
 */
@Composable
fun QuantitySelector(
    cantidad: Int,
    onCantidadChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Botón de Restar
        IconButton(onClick = { onCantidadChange(cantidad - 1) }) {
            Icon(Icons.Default.Remove, contentDescription = "Restar uno")
        }

        // Texto de Cantidad
        Text(
            text = "$cantidad",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(60.dp)
        )

        // Botón de Sumar
        IconButton(onClick = { onCantidadChange(cantidad + 1) }) {
            Icon(Icons.Default.Add, contentDescription = "Sumar uno", tint = MaterialTheme.colorScheme.primary)
        }
    }
}