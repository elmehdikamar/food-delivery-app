package com.mehdikamar.fooddelivery.service.impl

import com.mehdikamar.fooddelivery.service.model.ApiResponse
import com.mehdikamar.fooddelivery.service.model.response.FilterResponse
import com.mehdikamar.fooddelivery.service.model.response.OpenStatusResponse
import com.mehdikamar.fooddelivery.service.model.response.RestaurantsResponse
import com.mehdikamar.fooddelivery.service.ApiDataSource
import com.mehdikamar.fooddelivery.service.api.FoodDeliveryApi

class ApiDataSourceImpl(
    private val foodDeliveryApi: FoodDeliveryApi,
) : ApiDataSource {
    override fun getRestaurants(): ApiResponse<RestaurantsResponse> {
        return ApiResponse.create(foodDeliveryApi.getRestaurants().execute())
    }

    override fun getFilter(filterId: String): ApiResponse<FilterResponse> {
        return ApiResponse.create(foodDeliveryApi.getFilter(filterId).execute())
    }

    override fun getOpenStatus(restaurantId: String): ApiResponse<OpenStatusResponse> {
        return ApiResponse.create(foodDeliveryApi.getOpenStatus(restaurantId).execute())
    }
}