package com.example.foodtruck.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodtruck.data.repository.FoodTruckRepository
import com.example.foodtruck.domain.models.CartItem
import com.example.foodtruck.domain.models.FoodItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel(private val repository: FoodTruckRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _orderPlaced = MutableStateFlow(false)
    val orderPlaced = _orderPlaced.asStateFlow()

    private val _lastOrderId = MutableStateFlow<String?>(null)
    val lastOrderId = _lastOrderId.asStateFlow()

    // Items del carrito
    val cartItems = repository.getCartItems()

    // Conteo de items en el carrito
    val cartItemCount = repository.getCartItemCount()

    // Total del carrito calculado dinámicamente
    val cartTotal = cartItems.map { items ->
        calculateTotal(items)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    // Items del carrito con detalles de productos
    val cartItemsWithDetails = cartItems.map { cartItems ->
        cartItems.mapNotNull { cartItem ->
            // En una implementación real, harías join con FoodItem
            CartItemWithDetails(
                cartItem = cartItem,
                foodItem = null // Se poblará desde la UI
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    /**
     * Actualiza la cantidad de un item del carrito
     */
    fun updateQuantity(item: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                if (newQuantity <= 0) {
                    repository.removeFromCart(item)
                } else {
                    repository.updateCartItem(item.copy(quantity = newQuantity))
                }
            } catch (e: Exception) {
                // Manejar error
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Elimina un item del carrito
     */
    fun removeFromCart(item: CartItem) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.removeFromCart(item)
            } catch (e: Exception) {
                // Manejar error
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Vacía completamente el carrito
     */
    fun clearCart() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.clearCart()
            } catch (e: Exception) {
                // Manejar error
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Realiza un pedido con los items del carrito
     */
    fun placeOrder(customerName: String, customerPhone: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val orderId = repository.placeOrder(customerName, customerPhone)
                _lastOrderId.value = orderId
                _orderPlaced.value = true
            } catch (e: Exception) {
                // Manejar error
                _orderPlaced.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Resetea el estado de pedido realizado
     */
    fun resetOrderState() {
        _orderPlaced.value = false
        _lastOrderId.value = null
    }

    /**
     * Calcula el total del carrito
     */
    private suspend fun calculateTotal(cartItems: List<CartItem>): Double {
        return try {
            repository.getCartTotal()
        } catch (e: Exception) {
            // Fallback: cálculo simple
            cartItems.sumOf { it.quantity * 10.0 } // Precio promedio
        }
    }

    /**
     * Obtiene los detalles de un producto por ID
     */
    suspend fun getFoodItemById(id: String): FoodItem? {
        return repository.getItemById(id)
    }

    /**
     * Verifica si el carrito está vacío
     */
    fun isCartEmpty(): Flow<Boolean> {
        return cartItems.map { it.isEmpty() }
    }

    /**
     * Obtiene el tiempo estimado de preparación total
     */
    suspend fun getEstimatedPreparationTime(): Int {
        val items = cartItems.first()
        var maxTime = 0

        for (cartItem in items) {
            val foodItem = repository.getItemById(cartItem.foodItemId)
            if (foodItem != null) {
                maxTime = maxOf(maxTime, foodItem.preparationTime)
            }
        }

        return maxTime + 5 // 5 minutos adicionales de buffer
    }
}

/**
 * Data class para combinar CartItem con FoodItem
 */
data class CartItemWithDetails(
    val cartItem: CartItem,
    val foodItem: FoodItem?
)