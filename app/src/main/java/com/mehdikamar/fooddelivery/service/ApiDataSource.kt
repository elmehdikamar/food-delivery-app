package com.mehdikamar.fooddelivery.service

import com.mehdikamar.fooddelivery.service.model.ApiResponse
import com.mehdikamar.fooddelivery.service.model.response.FilterResponse
import com.mehdikamar.fooddelivery.service.model.response.OpenStatusResponse
import com.mehdikamar.fooddelivery.service.model.response.RestaurantsResponse

interface ApiDataSource {
    fun getRestaurants(): ApiResponse<RestaurantsResponse>
    fun getFilter(filterId: String): ApiResponse<FilterResponse>
    fun getOpenStatus(restaurantId: String): ApiResponse<OpenStatusResponse>
}