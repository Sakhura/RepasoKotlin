package com.example.foodtruck.data.database


import androidx.room.TypeConverter
import com.example.foodtruck.domain.models.OrderStatus


class Converters {

    @TypeConverter
    fun fromOrderStatus(status: OrderStatus): String {
        return status.name
    }

    @TypeConverter
    fun toOrderStatus(statusString: String): OrderStatus {
        return OrderStatus.valueOf(statusString)
    }
}

