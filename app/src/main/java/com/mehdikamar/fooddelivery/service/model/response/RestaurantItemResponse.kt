package com.mehdikamar.fooddelivery.service.model.response

import com.google.gson.annotations.SerializedName

data class RestaurantItemResponse(
    val id: String?,
    val name: String?,
    val rating: Double?,
    val filterIds: List<String>?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("delivery_time_minutes")
    val deliveryTimeMinutes: Int?
)