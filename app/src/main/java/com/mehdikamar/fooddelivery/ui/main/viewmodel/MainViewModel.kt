package com.mehdikamar.fooddelivery.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehdikamar.fooddelivery.model.Filter
import com.mehdikamar.fooddelivery.model.Restaurant
import com.mehdikamar.fooddelivery.repositories.FoodDeliveryRepository
import com.mehdikamar.fooddelivery.service.model.error.FoodDeliveryError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val foodDeliveryRepository: FoodDeliveryRepository
) : ViewModel() {

    val loadingObserver = MutableLiveData<Boolean>()
    fun getLoadingObserver(): LiveData<Boolean> = loadingObserver

    val detailLoadingObserver = MutableLiveData<Boolean>()
    fun getDetailLoadingObserver(): LiveData<Boolean> = detailLoadingObserver

    val filtersObserver = MutableLiveData<List<Filter>>()
    fun getFiltersObserver(): LiveData<List<Filter>> = filtersObserver

    val restaurantsObserver = MutableLiveData<List<Restaurant>>()
    fun getRestaurantsObserver(): LiveData<List<Restaurant>> = restaurantsObserver

    val selectedRestaurantObserver = MutableLiveData<Restaurant>()
    fun getSelectedRestaurantObserver(): LiveData<Restaurant> = selectedRestaurantObserver

    val openStatusObserver = MutableLiveData<Boolean>()
    fun getOpenStatusObserver(): LiveData<Boolean> = openStatusObserver

    val selectedFiltersObserver = MutableLiveData<List<String>>(mutableListOf())
    fun getSelectedFiltersObserver(): LiveData<List<String>> = selectedFiltersObserver

    val errorObserver = MutableLiveData<FoodDeliveryError?>()
    fun getErrorObserver(): LiveData<FoodDeliveryError?> = errorObserver

    fun getRestaurants() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                loadingObserver.postValue(true)
                foodDeliveryRepository.getRestaurants()
            }.onSuccess {
                if (it.isSuccessful()) {
                    restaurantsObserver.postValue(it.data ?: listOf())
                    var filterIds = mutableListOf<String>()
                    it.data?.forEach { restaurant ->
                        filterIds.addAll(restaurant.filterIds)
                    }
                    filterIds = filterIds.distinct().toMutableList()
                    if (filterIds.isNotEmpty()) getFilters(filterIds)
                } else {
                    errorObserver.postValue(it.error)
                    loadingObserver.postValue(false)
                }
            }.onFailure {
                it.printStackTrace()
                errorObserver.postValue(FoodDeliveryError())
                loadingObserver.postValue(false)
            }
        }
    }

    fun getFilters(filterIds: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                loadingObserver.postValue(true)
                foodDeliveryRepository.getAllFilters(filterIds)
            }.onSuccess {
                loadingObserver.postValue(false)
                if (it.isSuccessful()) {
                    filtersObserver.postValue(it.data ?: listOf())
                    restaurantsObserver.postValue(restaurantsObserver.value)
                } else {
                    errorObserver.postValue(it.error)
                }
            }.onFailure {
                it.printStackTrace()
                errorObserver.postValue(FoodDeliveryError())
                loadingObserver.postValue(false)
            }
        }
    }

    fun getOpenStatus(restaurant: Restaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                detailLoadingObserver.postValue(true)
                foodDeliveryRepository.getOpenStatus(restaurant.id)
            }.onSuccess {
                detailLoadingObserver.postValue(false)
                if (it.isSuccessful()) {
                    openStatusObserver.postValue(it.data ?: false)
                } else {
                    errorObserver.postValue(it.error)
                }
            }.onFailure {
                errorObserver.postValue(FoodDeliveryError())
                detailLoadingObserver.postValue(false)
            }
        }
    }

    fun addFilter(id: String) {
        val list = selectedFiltersObserver.value
        if (list?.contains(id) != true) selectedFiltersObserver.postValue(list?.plus(id))
    }

    fun removeFilter(id: String) {
        val list = selectedFiltersObserver.value
        if (list?.contains(id) == true) selectedFiltersObserver.postValue(list.minus(id))
    }

    fun selectRestaurant(restaurant: Restaurant) {
        selectedRestaurantObserver.postValue(restaurant)
    }
}