package com.pasteleria.ui.boleta

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pasteleria.R // Asegúrate de que R se importe correctamente para los drawables
import com.pasteleria.ui.theme.HuertohogarTheme
import com.pasteleria.ui.theme.Marron
import com.pasteleria.ui.theme.rosado

// NOTA: Re-utilizamos la data class y la lista de HomeScreem.
// Idealmente, esto debería estar en un archivo de modelo compartido.
data class Producto(
    val nombre: String,
    val precio: Int,
    val imagenResId: Int
)

val listaDeProductos = listOf(
    Producto("Torta de Chocolate", 14000, R.drawable.tortachocolate),
    Producto("Cupcakes (6 un.)", 2000, R.drawable.cupcake2),
    Producto("Donas (12 un.)", 2000, R.drawable.donut),
    Producto("Pie de Limón", 16000, R.drawable.pastry),
    Producto("Galletas Surtidas", 1500, R.drawable.cookie)
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoletaScreen(navController: NavController) {

    // Calculamos el total
    val total = listaDeProductos.sumOf { it.precio }

    HuertohogarTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Boleta / Resumen") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = rosado, // Usando el color de tu app
                        titleContentColor = Marron // Usando el color de tu app
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    "Resumen de Productos",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Lista de productos
                LazyColumn(
                    modifier = Modifier.weight(1f) // Ocupa el espacio disponible
                ) {
                    items(listaDeProductos) { producto ->
                        ProductoBoletaItem(producto = producto)
                        Divider() // Línea divisoria
                    }
                }

                // Total
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Total: $$total",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Composable
fun ProductoBoletaItem(producto: Producto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "1 unidad", // Asumimos 1 unidad por ahora
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        Text(
            text = "$${producto.precio}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}