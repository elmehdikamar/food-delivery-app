package com.mehdikamar.fooddelivery.service.model.response

import com.google.gson.annotations.SerializedName

data class FilterResponse(
    val id: String?,
    val name: String?,
    @SerializedName("image_url")
    val imageUrl: String?
)