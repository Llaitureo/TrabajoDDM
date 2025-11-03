package com.pasteleria.ui.boleta

import androidx.lifecycle.ViewModel
import com.pasteleria.ui.home.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BoletaViewModel : ViewModel() {

    private val _pedidos = MutableStateFlow<List<PedidoItem>>(emptyList())
    private val _userAge = MutableStateFlow<Int?>(null)
    private val _hasPromotionCode = MutableStateFlow<Boolean>(false)

    val pedidos: StateFlow<List<PedidoItem>> = _pedidos.asStateFlow()

    fun setUserDiscountInfo(age: Int?, hasPromotionCode: Boolean) {
        _userAge.value = age
        _hasPromotionCode.value = hasPromotionCode
    }

    fun limpiarBoletas() {
        _pedidos.value = emptyList()
        _userAge.value = null
        _hasPromotionCode.value = false
    }

    fun agregarPedido(producto: Producto, cantidad: Int) {
        val nuevoItem = PedidoItem(producto, cantidad)

        _pedidos.update { pedidos ->
            pedidos + nuevoItem
        }

        // Simular descuentos automáticamente (esto se haría normalmente con datos del usuario logueado)
        // Para demostración: usuario mayor de 50 años
        simulateUserDiscount()
    }

    private fun simulateUserDiscount() {
        // Simulación: alternamos entre diferentes tipos de descuentos para mostrar la funcionalidad
        val orderCount = _pedidos.value.size
        when (orderCount % 3) {
            1 -> setUserDiscountInfo(55, false) // Usuario mayor de 50 años
            2 -> setUserDiscountInfo(25, true)  // Usuario con código promocional
            else -> setUserDiscountInfo(30, false) // Usuario sin descuento
        }
    }

    fun calculateDiscount(): Double {
        // Descuento del 10% por código promocional "FELICES50"
        if (_hasPromotionCode.value == true) {
            return 0.10
        }

        // Descuento del 50% para mayores de 50 años
        val age = _userAge.value
        if (age != null && age >= 50) {
            return 0.50
        }

        return 0.0
    }

    fun getTotalWithDiscount(): Double {
        val subtotal = _pedidos.value.sumOf { it.producto.precio * it.cantidad }
        val discount = calculateDiscount()
        return subtotal * (1 - discount)
    }

    fun getDiscountAmount(): Double {
        val subtotal = _pedidos.value.sumOf { it.producto.precio * it.cantidad }
        val discount = calculateDiscount()
        return subtotal * discount
    }
}


