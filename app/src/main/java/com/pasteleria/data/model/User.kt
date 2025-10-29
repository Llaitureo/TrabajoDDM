package com.pasteleria.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users") // Nombre de la tabla en la BD
data class User(
    @PrimaryKey // El nombre de usuario será la clave única
    val username: String,
    val passwordHash: String // Guarda un hash de la contraseña, no el texto plano (por seguridad)
    // Puedes añadir más campos como email, nombre, etc. si lo necesitas
)