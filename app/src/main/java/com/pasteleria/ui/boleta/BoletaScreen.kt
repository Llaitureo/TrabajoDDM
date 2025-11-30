package com.pasteleria.ui.boleta

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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


    val context = LocalContext.current

    var mostrarDialogoConfirmacion by remember { mutableStateOf(false) }

    val subtotal = itemsDelPedido.sumOf { it.producto.precio * it.cantidad }
    val discountPercentage = boletaViewModel.calculateDiscount()
    val discountAmount = boletaViewModel.getDiscountAmount()
    val total = boletaViewModel.getTotalWithDiscount()

    // --- NUEVO: Lógica del Diálogo de Confirmación ---
    if (mostrarDialogoConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoConfirmacion = false },
            title = { Text(text = "Confirmar Compra") },
            text = {
                Text("¿Estás seguro de pagar un total de $${"%.0f".format(total)}?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarDialogoConfirmacion = false

                        // 1. Vaciar la base de datos (Carrito)
                        boletaViewModel.confirmarCompraYGuardarHistorial()

                        // 2. Mostrar mensaje de éxito
                        Toast.makeText(
                            context,
                            "¡Pago exitoso! Tu pedido está en proceso.",
                            Toast.LENGTH_LONG
                        ).show()

                        // 3. Volver al inicio (Home)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Marron)
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoConfirmacion = false }) {
                    Text("Cancelar", color = Color.Gray)
                }
            },
            containerColor = Color.White
        )
    }

    HuertohogarTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Mi Carrito") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = rosado,
                        titleContentColor = Marron
                    ),
                    actions = {
                        if (itemsDelPedido.isNotEmpty()) {
                            IconButton(onClick = { boletaViewModel.limpiarBoletas() }) {
                                Icon(Icons.Default.Delete, contentDescription = "Vaciar carrito", tint = Marron)
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                if (itemsDelPedido.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Tu carrito está vacío", style = MaterialTheme.typography.headlineSmall, color = Color.Gray)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { navController.popBackStack() }) {
                                Text("Ir a comprar")
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(itemsDelPedido) { item ->
                            ProductoBoletaCrudItem(
                                item = item,
                                onIncrement = { boletaViewModel.actualizarCantidad(item, item.cantidad + 1) },
                                onDecrement = { boletaViewModel.actualizarCantidad(item, item.cantidad - 1) },
                                onRemove = { boletaViewModel.eliminarPedido(item) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Tarjeta de Resumen
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            ResumenRow(label = "Subtotal:", value = "$$subtotal")

                            if (discountPercentage > 0) {
                                val labelDescuento = if (discountPercentage == 0.50) "Desc. Edad (50%)" else "Desc. Cupón (10%)"
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(labelDescuento, color = Color(0xFF2E8B57))
                                    Text("-$${"%.0f".format(discountAmount)}", color = Color(0xFF2E8B57))
                                }
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                Text("$${"%.0f".format(total)}", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Marron)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            mostrarDialogoConfirmacion = true
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Marron)
                    ) {
                        Text("Pagar ahora", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoBoletaCrudItem(
    item: PedidoItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Marron
                )
                Text(
                    text = "$${item.producto.precio} c/u",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = "Total: $${item.producto.precio * item.cantidad}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Marron
                )
            }

            // Controles CRUD (Menos, Cantidad, Más, Borrar)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrement, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Remove, contentDescription = "Restar", tint = Marron)
                }

                Text(
                    text = "${item.cantidad}",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                IconButton(onClick = onIncrement, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Add, contentDescription = "Sumar", tint = Marron)
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = onRemove, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                }
            }
        }
    }
}

@Composable
fun ResumenRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(value)
    }
}