package com.pasteleria.ui.boleta

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pasteleria.data.database.AppDatabase
import com.pasteleria.data.model.PedidoEntity
import com.pasteleria.data.model.Producto
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.pasteleria.data.model.OrdenEntity
import com.pasteleria.data.model.OrdenDetalleEntity
import java.util.Date

class BoletaViewModel(application: Application) : AndroidViewModel(application) {

    private val pedidoDao = AppDatabase.getDatabase(application).pedidoDao()
    private val productoDao = AppDatabase.getDatabase(application).productoDao()

    private val _currentUsername = MutableStateFlow("")

    // Estados para descuentos
    private val _userAge = MutableStateFlow<Int?>(null)
    private val _hasPromotionCode = MutableStateFlow<Boolean>(false)
    private val ordenDao = AppDatabase.getDatabase(application).ordenDao()

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val pedidos: StateFlow<List<PedidoItem>> = _currentUsername.flatMapLatest { username ->
        if (username.isNotEmpty()) {
            pedidoDao.obtenerCarrito(username).map { listaDb ->
                listaDb.map {
                    PedidoItem(it.producto, it.pedido.cantidad)
                }
            }
        } else {
            flowOf(emptyList())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setUsuarioActual(username: String) {
        _currentUsername.value = username
    }

    fun agregarPedido(productoUI: Producto, cantidad: Int) {
        val user = _currentUsername.value
        if (user.isEmpty()) return

        viewModelScope.launch {
            // 1. Obtener ID real del producto
            var productoReal = productoDao.obtenerPorNombre(productoUI.nombre)

            val realId = if (productoReal != null) {
                productoReal.id
            } else {
                val newId = productoDao.insert(productoUI)
                if (newId == -1L) {
                    productoDao.obtenerPorNombre(productoUI.nombre)?.id ?: 0
                } else {
                    newId.toInt()
                }
            }

            if (realId == 0) return@launch

            // 2. Guardar en el carrito
            val pedidoExistente = pedidoDao.obtenerPedidoEspecifico(user, realId)

            val nuevaCantidad = if (pedidoExistente != null) {
                pedidoExistente.cantidad + cantidad
            } else {
                cantidad
            }

            val pedidoEntity = PedidoEntity(
                username = user,
                productoId = realId,
                cantidad = nuevaCantidad
            )
            pedidoDao.insertarPedido(pedidoEntity)

            simulateUserDiscount()
        }
    }

    // CRUD: Actualizar cantidad directa
    fun actualizarCantidad(item: PedidoItem, nuevaCantidad: Int) {
        val user = _currentUsername.value
        if (user.isEmpty()) return

        viewModelScope.launch {
            if (nuevaCantidad <= 0) {
                // Eliminar si es 0
                val entidad = PedidoEntity(user, item.producto.id, item.cantidad)
                pedidoDao.eliminarPedido(entidad)
            } else {
                // Actualizar
                val entidad = PedidoEntity(
                    username = user,
                    productoId = item.producto.id,
                    cantidad = nuevaCantidad
                )
                pedidoDao.insertarPedido(entidad)
            }
        }
    }

    // CRUD: Eliminar
    fun eliminarPedido(item: PedidoItem) {
        val user = _currentUsername.value
        if (user.isEmpty()) return

        viewModelScope.launch {
            val entidad = PedidoEntity(user, item.producto.id, item.cantidad)
            pedidoDao.eliminarPedido(entidad)
        }
    }
    fun confirmarCompraYGuardarHistorial() {
        val user = _currentUsername.value
        val itemsActuales = pedidos.value
        val totalCompra = getTotalWithDiscount()

        if (user.isEmpty() || itemsActuales.isEmpty()) return

        viewModelScope.launch {
            // 1. Crear la cabecera de la orden
            val nuevaOrden = OrdenEntity(
                username = user,
                fecha = System.currentTimeMillis(),
                total = totalCompra
            )

            // 2. Insertar orden y obtener su ID
            val ordenId = ordenDao.insertarOrden(nuevaOrden)

            // 3. Crear lista de detalles basada en el carrito actual
            val detalles = itemsActuales.map { item ->
                OrdenDetalleEntity(
                    ordenIdOwner = ordenId,
                    nombreProducto = item.producto.nombre,
                    cantidad = item.cantidad,
                    precioUnitario = item.producto.precio
                )
            }

            // 4. Guardar detalles
            ordenDao.insertarDetalles(detalles)

            // 5. Vaciar el carrito (Borrar tabla temporal)
            pedidoDao.vaciarCarrito(user)

            // Resetear estados UI
            _userAge.value = null
            _hasPromotionCode.value = false
        }
    }

    fun limpiarBoletas() {
        val user = _currentUsername.value
        // Si no hay usuario identificado, no hacemos nada por seguridad
        if (user.isEmpty()) return

        viewModelScope.launch {
            // 1. Borra todos los items de la tabla pedidos_carrito para este usuario
            pedidoDao.vaciarCarrito(user)

            // 2. Resetea los valores temporales de la vista (descuentos aplicados)
            _userAge.value = null
            _hasPromotionCode.value = false
        }
    }



    fun setUserDiscountInfo(age: Int?, hasPromotionCode: Boolean) {
        _userAge.value = age
        _hasPromotionCode.value = hasPromotionCode
    }

    private fun simulateUserDiscount() {
        val orderCount = pedidos.value.size
        when (orderCount % 3) {
            1 -> setUserDiscountInfo(55, false)
            2 -> setUserDiscountInfo(25, true)
            else -> setUserDiscountInfo(30, false)
        }
    }

    fun calculateDiscount(): Double {
        if (_hasPromotionCode.value == true) return 0.10
        val age = _userAge.value
        if (age != null && age >= 50) return 0.50
        return 0.0
    }

    fun getTotalWithDiscount(): Double {
        val currentList = pedidos.value
        val subtotal = currentList.sumOf { it.producto.precio * it.cantidad }
        val discount = calculateDiscount()
        return subtotal * (1 - discount)
    }

    fun getDiscountAmount(): Double {
        val currentList = pedidos.value
        val subtotal = currentList.sumOf { it.producto.precio * it.cantidad }
        val discount = calculateDiscount()
        return subtotal * discount
    }
}


