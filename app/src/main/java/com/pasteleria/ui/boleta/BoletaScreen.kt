package com.pasteleria.ui.boleta

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pasteleria.ui.theme.HuertohogarTheme
import com.pasteleria.ui.theme.Marron
import com.pasteleria.ui.theme.rosado


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoletaScreen(
    navController: NavController,
    boletaViewModel: BoletaViewModel
) {

    val itemsDelPedido by boletaViewModel.pedidos.collectAsState()

    val total = itemsDelPedido.sumOf { it.producto.precio * it.cantidad }

    HuertohogarTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Boleta / Resumen") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {//sin esto no hay como salir.
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = rosado,
                        titleContentColor = Marron
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

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(itemsDelPedido) { item ->
                        ProductoBoletaItem(item = item)
                        Divider()
                    }
                }

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
fun ProductoBoletaItem(item: PedidoItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.producto.nombre,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${item.cantidad} unidad(es)",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        Text(//Total General
            text = "$${item.producto.precio * item.cantidad}",//Muestra lo añadido
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
