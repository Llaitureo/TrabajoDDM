package com.pasteleria.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val username: String,
    val passwordHash: String // Guarda un hash de la contraseña, no el texto plano (por seguridad)
)