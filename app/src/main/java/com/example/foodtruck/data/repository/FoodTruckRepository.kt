package com.example.foodtruck.data.repository

import com.example.foodtruck.data.database.FoodTruckDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

import com.example.foodtruck.domain.models.*

class FoodTruckRepository(private val dao: FoodTruckDao) {

    // === FOOD ITEMS ===
    fun getAllAvailableItems(): Flow<List<FoodItem>> = dao.getAllAvailableItems()

    fun getItemsByCategory(category: String): Flow<List<FoodItem>> = dao.getItemsByCategory(category)

    suspend fun getItemById(id: String): FoodItem? = dao.getItemById(id)

    fun getCategories(): Flow<List<String>> = dao.getCategories()

    // === CART ===
    fun getCartItems(): Flow<List<CartItem>> = dao.getCartItems()

    fun getCartItemCount(): Flow<Int> = dao.getCartItemCount()

    suspend fun addToCart(foodItem: FoodItem, quantity: Int, instructions: String = "") {
        val cartItem = CartItem(
            foodItemId = foodItem.id,
            quantity = quantity,
            specialInstructions = instructions
        )
        dao.addToCart(cartItem)
    }

    suspend fun updateCartItem(item: CartItem) = dao.updateCartItem(item)

    suspend fun removeFromCart(item: CartItem) = dao.removeFromCart(item)

    suspend fun clearCart() = dao.clearCart()

    suspend fun getCartTotal(): Double {
        val cartItems = dao.getCartItems().first()
        return calculateCartTotal(cartItems)
    }

    // === ORDERS ===
    fun getAllOrders(): Flow<List<Order>> = dao.getAllOrders()

    suspend fun placeOrder(customerName: String, customerPhone: String): String {
        val cartItems = dao.getCartItems().first()
        val totalAmount = calculateCartTotal(cartItems)
        val orderId = "ORD${System.currentTimeMillis()}"

        val order = Order(
            id = orderId,
            customerName = customerName,
            customerPhone = customerPhone,
            items = cartItems.toString(),
            totalAmount = totalAmount,
            status = OrderStatus.PENDING,
            orderTime = System.currentTimeMillis()
        )

        dao.insertOrder(order)
        dao.clearCart()
        return orderId
    }

    // === HELPERS ===
    private suspend fun calculateCartTotal(cartItems: List<CartItem>): Double {
        var total = 0.0
        for (cartItem in cartItems) {
            val foodItem = dao.getItemById(cartItem.foodItemId)
            if (foodItem != null) {
                total += foodItem.price * cartItem.quantity
            }
        }
        return total
    }

    // === DATOS DE EJEMPLO ===
    suspend fun initializeSampleData() {
        val existingItems = dao.getAllAvailableItems().first()
        if (existingItems.isEmpty()) {
            val sampleItems = listOf(
                FoodItem("1", "Hamburguesa Clásica", "Carne de res, lechuga, tomate, queso cheddar", 12.99, "Hamburguesas", "", true, 12, 4.5),
                FoodItem("2", "Hot Dog Especial", "Salchicha premium con cebolla caramelizada", 8.50, "Hot Dogs", "", true, 8, 4.3),
                FoodItem("3", "Tacos de Carnitas", "3 tacos con carne de cerdo, cebolla y cilantro", 15.99, "Tacos", "", true, 10, 4.7),
                FoodItem("4", "Quesadilla de Pollo", "Tortilla con pollo asado y queso oaxaca", 11.99, "Quesadillas", "", true, 9, 4.4),
                FoodItem("5", "Papas Fritas Gourmet", "Porción grande con especias especiales", 6.99, "Acompañamientos", "", true, 5, 4.2),
                FoodItem("6", "Agua Fresca", "Horchata, jamaica o tamarindo", 3.50, "Bebidas", "", true, 2, 4.0),
                FoodItem("7", "Burrito Mexicano", "Grande con frijoles, arroz, carne asada", 16.99, "Burritos", "", true, 15, 4.6),
                FoodItem("8", "Nachos Supreme", "Con queso derretido, jalapeños y crema", 13.50, "Aperitivos", "", true, 8, 4.4),
                FoodItem("9", "Elote Loco", "Mazorca con mayonesa, queso y chile", 5.99, "Antojitos", "", true, 3, 4.3),
                FoodItem("10", "Agua de Coco", "Natural y refrescante", 4.50, "Bebidas", "", true, 1, 4.1)
            )
            dao.insertFoodItems(sampleItems)
        }
    }
}