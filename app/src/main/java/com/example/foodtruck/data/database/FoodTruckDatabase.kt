package com.example.foodtruck.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodtruck.domain.models.FoodItem
import com.example.foodtruck.domain.models.CartItem
import com.example.foodtruck.domain.models.Order

@Database(
    entities = [
        FoodItem::class,
        CartItem::class,
        Order::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FoodTruckDatabase : RoomDatabase() {

    abstract fun dao(): FoodTruckDao

    companion object {
        @Volatile
        private var INSTANCE: FoodTruckDatabase? = null

        fun getDatabase(context: Context): FoodTruckDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodTruckDatabase::class.java,
                    "foodtruck_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}