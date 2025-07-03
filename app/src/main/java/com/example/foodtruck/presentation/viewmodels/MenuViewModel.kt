package com.example.foodtruck.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodtruck.data.repository.FoodTruckRepository
import com.example.foodtruck.domain.models.FoodItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MenuViewModel(private val repository: FoodTruckRepository) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Obtener todos los items del menú
    val allItems = repository.getAllAvailableItems()

    // Obtener categorías disponibles
    val categories = repository.getCategories().map { list ->
        listOf("Todos") + list
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf("Todos")
    )

    // Items filtrados por categoría seleccionada
    val filteredItems = combine(allItems, selectedCategory) { items, category ->
        if (category == "Todos") {
            items
        } else {
            items.filter { it.category == category }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    /**
     * Selecciona una categoría para filtrar el menú
     */
    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    /**
     * Añade un item al carrito
     */
    fun addToCart(item: FoodItem, quantity: Int, instructions: String = "") {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.addToCart(item, quantity, instructions)
            } catch (e: Exception) {
                // Manejar error (podrías agregar un estado de error)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Busca items por nombre o descripción
     */
    fun searchItems(query: String): Flow<List<FoodItem>> {
        return allItems.map { items ->
            if (query.isBlank()) {
                items
            } else {
                items.filter { item ->
                    item.name.contains(query, ignoreCase = true) ||
                            item.description.contains(query, ignoreCase = true)
                }
            }
        }
    }

    /**
     * Obtiene los items más populares
     */
    fun getPopularItems(): Flow<List<FoodItem>> {
        return allItems.map { items ->
            items.sortedByDescending { it.rating }.take(5)
        }
    }

    init {
        // Inicializar datos de ejemplo al crear el ViewModel
        viewModelScope.launch {
            repository.initializeSampleData()
        }
    }
}