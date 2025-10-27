package com.pasteleria.ui.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pasteleria.R // Importa tus recursos, incluyendo R.drawable.cupcake
import com.pasteleria.ui.login.LoginViewModel
import com.pasteleria.ui.theme.HuertohogarTheme
import com.pasteleria.ui.theme.Marron
import com.pasteleria.ui.theme.MarronClaro
import com.pasteleria.ui.theme.TextoPrincipal
import com.pasteleria.ui.theme.VerdeEsmeralda
import com.pasteleria.ui.theme.rosado

// 1. Un data class simple para representar un producto
data class Producto(
    val nombre: String,
    val precio: Int,
    val imagenResId: Int // Usaremos un ID de drawable
)

// 2. Lista de productos de ejemplo (puedes mover esto a un ViewModel más tarde)
val listaDeProductos = listOf(
    Producto("Torta de Chocolate", 14000, R.drawable.tortachocolate),
    Producto("Cupcakes (6 un.)", 2000, R.drawable.cupcake2),
    Producto("Donas (12 un.)", 2000, R.drawable.donut),
    Producto("Pie de Limón", 16000, R.drawable.pastry),
    Producto("Galletas Surtidas", 1500, R.drawable.cookie)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreem(
    username: String,
    navController: NavController
) {
    HuertohogarTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "¡Hola, $username!") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = rosado, // Color de tu tema
                        titleContentColor = Marron
                    )
                )
            }
        ) { innerPadding ->
            // Usamos LazyColumn para una lista eficiente
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre tarjetas
            ) {
                // Título de la sección
                item {
                    Text(
                        text = "Nuestros Productos",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Creamos un item por cada producto en la lista
                items(listaDeProductos) { producto ->
                    ProductoCard(
                        producto = producto,
                        onProductoClick = {
                            val nombreProducto = Uri.encode(producto.nombre)
                            val precio = producto.precio
                            val imagenResId = producto.imagenResId

                            navController.navigate("PedidoScreen/$nombreProducto/$precio/$imagenResId")
                        }
                    )
                }

                // Botón de cerrar sesión al final
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VerdeEsmeralda
                        )
                    ) {
                        Text("Cerrar sesión")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoCard(
    producto: Producto,

    onProductoClick: () -> Unit // Acción al hacer clic
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onProductoClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface // Color de fondo de la tarjeta
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- Imagen ---
            Image(
                painter = painterResource(id = producto.imagenResId),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.medium), // Bordes redondeados
                contentScale = ContentScale.Crop // Asegura que la imagen llene el espacio
            )

            Spacer(modifier = Modifier.width(16.dp))

            // --- Título y Precio ---
            Column(
                modifier = Modifier.weight(1f) // Ocupa el espacio restante
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${producto.precio}", // Formato de precio
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary, // Color destacado
                    fontSize = 16.sp
                )
            }
        }
    }


}
@Preview (showBackground = true)
@Composable
fun HomeScreemPreview(){
    val navController = rememberNavController()
    HomeScreem(username = "admin", navController)
}