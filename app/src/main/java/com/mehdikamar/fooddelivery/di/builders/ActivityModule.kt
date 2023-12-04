package com.mehdikamar.fooddelivery.di.builders

import com.mehdikamar.fooddelivery.ui.main.RestaurantMainActivity
import com.mehdikamar.fooddelivery.ui.restaurant.RestaurantDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector()
    internal abstract fun mainActivity(): RestaurantMainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeRestaurantDetailFragment(): RestaurantDetailFragment
}