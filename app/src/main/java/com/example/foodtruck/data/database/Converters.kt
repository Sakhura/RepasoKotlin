import androidx.room.TypeConverter


@TypeConverter
fun fromOrderStatus(status: OrderStatus): String{
    return status.nombre
}

@TypeConverter
fun toOrderStatus(statusString: String): OrderStatus{
    return OrderStatus.valueOf(statusString)
}

@TypeConverter
fun fromStringList(value: List<String>): String{
    return value.joinToString(",") 
}



