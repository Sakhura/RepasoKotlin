package com.example.foodtruck.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.foodtruck.domain.models.*

@Dao
interface FoodTruckDao {

    // === FOOD ITEMS ===
    @Query("SELECT * FROM food_items WHERE isAvailable = 1 ORDER BY category, name")
    fun getAllAvailableItems(): Flow<List<FoodItem>>

    @Query("SELECT * FROM food_items WHERE category = :category AND isAvailable = 1")
    fun getItemsByCategory(category: String): Flow<List<FoodItem>>

    @Query("SELECT * FROM food_items WHERE id = :id")
    suspend fun getItemById(id: String): FoodItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodItems(items: List<FoodItem>)

    @Query("SELECT DISTINCT category FROM food_items WHERE isAvailable = 1 ORDER BY category")
    fun getCategories(): Flow<List<String>>

    // === CART ===
    @Query("SELECT * FROM cart_items ORDER BY id DESC")
    fun getCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(item: CartItem)

    @Update
    suspend fun updateCartItem(item: CartItem)

    @Delete
    suspend fun removeFromCart(item: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT COUNT(*) FROM cart_items")
    fun getCartItemCount(): Flow<Int>

    // === ORDERS ===
    @Query("SELECT * FROM orders ORDER BY orderTime DESC")
    fun getAllOrders(): Flow<List<Order>>

    @Query("SELECT * FROM orders WHERE status = :status ORDER BY orderTime DESC")
    fun getOrdersByStatus(status: OrderStatus): Flow<List<Order>>

    @Insert
    suspend fun insertOrder(order: Order)

    @Update
    suspend fun updateOrder(order: Order)

    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderById(orderId: String): Order?
}