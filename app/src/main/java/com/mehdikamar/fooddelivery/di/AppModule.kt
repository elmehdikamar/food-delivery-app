package com.mehdikamar.fooddelivery.di

import com.mehdikamar.fooddelivery.repositories.FoodDeliveryRepository
import com.mehdikamar.fooddelivery.repositories.impl.FoodDeliveryRepositoryImpl
import com.mehdikamar.fooddelivery.service.ApiDataSource
import com.mehdikamar.fooddelivery.service.api.FoodDeliveryApi
import com.mehdikamar.fooddelivery.service.impl.ApiDataSourceImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideFoodDeliveryApi(networkService: Retrofit): FoodDeliveryApi =
        networkService.create(FoodDeliveryApi::class.java)

    @Singleton
    @Provides
    fun provideFoodDeliveryRepository(apiDataSource: ApiDataSource): FoodDeliveryRepository =
        FoodDeliveryRepositoryImpl(apiDataSource)

    @Singleton
    @Provides
    fun provideApiDataSource(foodDeliveryApi: FoodDeliveryApi): ApiDataSource =
        ApiDataSourceImpl(foodDeliveryApi)
}