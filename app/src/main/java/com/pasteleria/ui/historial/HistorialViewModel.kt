package com.pasteleria.ui.historial

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pasteleria.data.database.AppDatabase
import com.pasteleria.data.model.OrdenConDetalles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistorialViewModel(application: Application) : AndroidViewModel(application) {
    private val ordenDao = AppDatabase.getDatabase(application).ordenDao()

    private val _historial = MutableStateFlow<List<OrdenConDetalles>>(emptyList())
    val historial: StateFlow<List<OrdenConDetalles>> = _historial.asStateFlow()

    fun cargarHistorial(username: String) {
        viewModelScope.launch {
            ordenDao.obtenerHistorial(username).collect { lista ->
                _historial.value = lista
            }
        }
    }
}