package com.mehdikamar.fooddelivery.service.model

import com.google.gson.Gson
import com.mehdikamar.fooddelivery.service.model.error.FoodDeliveryError
import retrofit2.Response

sealed class ApiResponse<T> {

    companion object {

        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(FoodDeliveryError.build())
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body = body)
                }
            } else {
                val errorString = response.errorBody()?.string() ?: response.body().toString()
                val foodDeliveryError = try {
                    Gson().fromJson(errorString, FoodDeliveryError::class.java)
                } catch (e: Exception) {
                    FoodDeliveryError.build()
                }
                ApiErrorResponse(foodDeliveryError)
            }
        }
    }
}


class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val foodDeliveryError: FoodDeliveryError) : ApiResponse<T>()