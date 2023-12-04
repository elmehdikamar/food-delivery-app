package com.mehdikamar.fooddelivery.model

data class Restaurant(
    val id: String,
    val name: String,
    val filterIds: List<String>,
    val rating: String,
    val imageUrl: String?,
    val deliveryTimeMinutes: String
)