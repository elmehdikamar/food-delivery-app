package com.mehdikamar.fooddelivery.service.api

import com.mehdikamar.fooddelivery.service.model.response.FilterResponse
import com.mehdikamar.fooddelivery.service.model.response.OpenStatusResponse
import com.mehdikamar.fooddelivery.service.model.response.RestaurantsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodDeliveryApi {
    @GET("/api/v1/restaurants")
    fun getRestaurants(): Call<RestaurantsResponse>

    @GET("/api/v1/filter/{filterId}")
    fun getFilter(@Path("filterId") filterId: String?): Call<FilterResponse>

    @GET("/api/v1/open/{id}")
    fun getOpenStatus(@Path("id") restaurantId: String?): Call<OpenStatusResponse>
}