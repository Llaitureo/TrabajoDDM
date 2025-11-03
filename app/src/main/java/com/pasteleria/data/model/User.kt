package com.pasteleria.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val passwordHash: String,
    val birthDate: String? = null,
    val discountCode: String? = null,
    val hasFiftyPercentDiscount: Boolean = false,
    val hasTenPercentDiscount: Boolean = false,
    val hasFreeCakeOnBirthday: Boolean = false
)