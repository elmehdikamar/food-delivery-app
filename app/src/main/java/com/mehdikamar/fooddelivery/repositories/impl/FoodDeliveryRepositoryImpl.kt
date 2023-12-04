package com.mehdikamar.fooddelivery.repositories.impl

import com.mehdikamar.fooddelivery.model.Filter
import com.mehdikamar.fooddelivery.model.Restaurant
import com.mehdikamar.fooddelivery.repositories.FoodDeliveryRepository
import com.mehdikamar.fooddelivery.service.ApiDataSource
import com.mehdikamar.fooddelivery.service.model.ApiEmptyResponse
import com.mehdikamar.fooddelivery.service.model.ApiErrorResponse
import com.mehdikamar.fooddelivery.service.model.ApiSuccessResponse
import com.mehdikamar.fooddelivery.service.model.Resource
import com.mehdikamar.fooddelivery.service.model.error.FoodDeliveryError

class FoodDeliveryRepositoryImpl(private val apiDataSource: ApiDataSource) :
    FoodDeliveryRepository {
    override fun getRestaurants(): Resource<List<Restaurant>> {
        return when (val result = apiDataSource.getRestaurants()) {
            is ApiSuccessResponse -> {
                Resource.success(result.body.restaurants.map {
                    Restaurant(
                        id = it.id ?: "",
                        name = it.name ?: "",
                        filterIds = it.filterIds ?: listOf(),
                        deliveryTimeMinutes = it.deliveryTimeMinutes?.toString() ?: "0",
                        imageUrl = it.imageUrl,
                        rating = it.rating?.toString() ?: "0"
                    )
                }.toList())
            }

            is ApiEmptyResponse -> {
                Resource.error(FoodDeliveryError())
            }

            is ApiErrorResponse -> {
                Resource.error(FoodDeliveryError())
            }
        }
    }

    override fun getFilter(filterId: String): Resource<Filter> {
        return when (val result = apiDataSource.getFilter(filterId)) {
            is ApiSuccessResponse -> {
                Resource.success(result.body.let {
                    Filter(
                        id = it.id ?: "",
                        name = it.name ?: "",
                        imageUrl = it.imageUrl
                    )
                })
            }

            is ApiEmptyResponse -> {
                Resource.error(FoodDeliveryError())
            }

            is ApiErrorResponse -> {
                Resource.error(FoodDeliveryError())
            }
        }
    }

    override fun getAllFilters(filtersIds: List<String>): Resource<List<Filter>> {
        val filters = mutableListOf<Filter>()
        filtersIds.forEach { filterId ->
            getFilter(filterId).data?.let {
                filters.add(it)
            }
        }
        return Resource.success(filters)
    }

    override fun getOpenStatus(restaurantId: String): Resource<Boolean> {
        return when (val result = apiDataSource.getOpenStatus(restaurantId)) {
            is ApiSuccessResponse -> {
                Resource.success(result.body.isCurrentlyOpen)
            }

            is ApiEmptyResponse -> {
                Resource.error(FoodDeliveryError())
            }

            is ApiErrorResponse -> {
                Resource.error(FoodDeliveryError())
            }
        }
    }
}