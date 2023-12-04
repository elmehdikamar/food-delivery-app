package com.mehdikamar.fooddelivery.service.model.error

import com.google.gson.annotations.SerializedName

open class FoodDeliveryError {
    @SerializedName("error")
    var error: Boolean = true

    @SerializedName("reason")
    var reason: String? = null

    companion object {
        fun build(): FoodDeliveryError {
            val obgError = FoodDeliveryError()
            obgError.error = true
            obgError.reason = "There was an error"
            return obgError
        }
    }
}