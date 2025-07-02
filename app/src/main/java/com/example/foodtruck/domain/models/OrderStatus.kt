package com.example.foodtruck.domain.models


enum class OrderStatus {
    PENDING,    // Pendiente
    PREPARING,  // En preparación
    READY,      // Listo para recoger
    COMPLETED,  // Completado
    CANCELLED   // Cancelado
}