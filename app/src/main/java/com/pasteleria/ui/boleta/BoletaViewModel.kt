package com.pasteleria.ui.boleta

import androidx.lifecycle.ViewModel
import com.pasteleria.ui.home.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BoletaViewModel : ViewModel() {

    private val _pedidos = MutableStateFlow<List<PedidoItem>>(emptyList())

    val pedidos: StateFlow<List<PedidoItem>> = _pedidos.asStateFlow()

    fun agregarPedido(producto: Producto, cantidad: Int) {
        val nuevoItem = PedidoItem(producto, cantidad)

        _pedidos.update { pedidos ->
            pedidos + nuevoItem
        }
    }
}


