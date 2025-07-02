import androix.room.*
import com.example.domain.model.*
import kotlinx.coroutines.flow.flow

@dao
Interface FoodTruckDao {

         @Query("SELECT * FROM food_items WHERE id = :id ")
        suspend fun getItemById(Id: String): FoodItem?

    @Insert(onConFlict = onConFlictStrategy.REPLACE)
    suspend fun insertFoodItems(item: List<FoodItem>)

    @Query("SELECT * FROM food_item WHERE isAvailable = 1 OREDER BY category, name")
    fun getAllAvailableItems(): Flow<List>FoodItem>>

    
}
