package com.mehdikamar.fooddelivery

import android.util.Log
import com.mehdikamar.fooddelivery.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class FoodDeliveryApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().context(this).build()
    }
}