package com.mehdikamar.fooddelivery.di

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.mehdikamar.fooddelivery.FoodDeliveryApplication
import com.mehdikamar.fooddelivery.di.builders.ActivityModule
import com.mehdikamar.fooddelivery.di.builders.ViewModelModule
import com.mehdikamar.fooddelivery.ui.main.RestaurantMainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        NetworkModule::class,
    ]
)
interface AppComponent: AndroidInjector<FoodDeliveryApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    override fun inject(app: FoodDeliveryApplication)
}