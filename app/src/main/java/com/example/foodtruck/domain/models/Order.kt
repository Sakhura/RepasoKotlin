package com.example.foodtruck.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "orders")
data class Order(
    @PrimaryKey
    val id: String,
    val customerName: String,
    val customerPhone: String,
    val items: String, // JSON string de los items
    val totalAmount: Double,
    val status: OrderStatus,
    val orderTime: Long,
    val pickupTime: Long? = null
) : Parcelable