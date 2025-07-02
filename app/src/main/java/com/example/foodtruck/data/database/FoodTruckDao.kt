import androix.room.*
import com.example.domain.model.*
import kotlinx.coroutines.flow.flow

@dao
Interface FoodTruckDao {

         @Query("SELECT * FROM food_items WHERE id = :id ")
        suspend fun getItemById(Id: String): FoodItem?

    @Insert(onConFlict = onConFlictStrategy.REPLACE)
    suspend fun insertFoodItems(item: List<FoodItem>)

}
