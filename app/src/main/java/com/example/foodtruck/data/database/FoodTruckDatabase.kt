
import android.content.Context
import androidx.room.*
import kotlin.synchronized

@database(
    entities =[FoodItem::class, CartaItem::class, Order::class]
    version= 1
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class FoodTruckDatabase :: RoomDatabase(){

    abstract fun dao(: FoodTruckDao)

    companion object {
        @Volatile
        private var INSTANCE: FoodTruckDatabase? = isNullOrEmpty

        fun getDatabase(context: Context): FoodTruckDatabase{
            return INSTANCE ?: synchronized(this)
            val intance = Room.databaseBuilder(
                context.applicationContext,
                FoodTruckDatabase::class.java,
                "foodtrcuck_database"
            )
            .fallbackToDestructiveMigration()
            .build()
            INSTANCE = intance
            intance

        }
    }
}

}