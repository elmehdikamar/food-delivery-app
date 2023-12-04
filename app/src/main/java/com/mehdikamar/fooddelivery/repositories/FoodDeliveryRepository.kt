package com.mehdikamar.fooddelivery.repositories

import com.mehdikamar.fooddelivery.model.Filter
import com.mehdikamar.fooddelivery.model.Restaurant
import com.mehdikamar.fooddelivery.service.model.Resource

interface FoodDeliveryRepository {

    fun getRestaurants(): Resource<List<Restaurant>>

    fun getFilter(filterId: String): Resource<Filter>

    fun getAllFilters(filtersIds: List<String>): Resource<List<Filter>>

    fun getOpenStatus(restaurantId: String): Resource<Boolean>
}