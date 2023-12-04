package com.mehdikamar.fooddelivery.service.model

import com.mehdikamar.fooddelivery.service.model.error.FoodDeliveryError

class Resource<T> constructor(var status: Status, var data: T?, var error: FoodDeliveryError? = null) {

    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(error: FoodDeliveryError, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, error)
        }
    }

    fun isSuccessful() : Boolean = status == Status.SUCCESS

    override fun toString(): String {
        return "Resource{" +
                "status=" + status +
                "message='" + error?.reason + '\'' +
                ", data=" + data +
                '}'
    }
}