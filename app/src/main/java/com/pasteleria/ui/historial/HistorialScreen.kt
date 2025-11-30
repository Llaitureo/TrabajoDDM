package com.pasteleria.ui.historial

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pasteleria.data.model.OrdenConDetalles
import com.pasteleria.ui.theme.Marron
import com.pasteleria.ui.theme.rosado
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(
    navController: NavController,
    username: String,
    viewModel: HistorialViewModel = viewModel()
) {
    // Cargar historial al iniciar
    LaunchedEffect(username) {
        viewModel.cargarHistorial(username)
    }

    val listaHistorial by viewModel.historial.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Compras Pasadas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            if (listaHistorial.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tienes compras registradas aún.", color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(listaHistorial) { orden ->
                        OrdenItem(orden)
                    }
                }
            }
        }
    }
}

@Composable
fun OrdenItem(ordenCompleta: OrdenConDetalles) {
    var expanded by remember { mutableStateOf(false) }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val fechaStr = dateFormat.format(Date(ordenCompleta.orden.fecha))

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Cabecera de la Tarjeta
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Boleta #${ordenCompleta.orden.ordenId}", fontWeight = FontWeight.Bold, color = Marron)
                    Text(text = fechaStr, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$${"%.0f".format(ordenCompleta.orden.total)}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Marron
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Ver detalles"
                    )
                }
            }

            // Detalles Expandibles
            if (expanded) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Column {
                    ordenCompleta.detalles.forEach { detalle ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${detalle.cantidad}x ${detalle.nombreProducto}")
                            Text("$${detalle.cantidad * detalle.precioUnitario}")
                        }
                    }
                }
            }
        }
    }
}