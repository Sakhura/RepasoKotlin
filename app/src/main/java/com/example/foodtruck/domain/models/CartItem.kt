package com.example.foodtruck.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "food_items")
data class FoodItem(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String = "",
    val isAvailable: Boolean = true,
    val preparationTime: Int, // en minutos
    val rating: Double = 4.5
) : Parcelable