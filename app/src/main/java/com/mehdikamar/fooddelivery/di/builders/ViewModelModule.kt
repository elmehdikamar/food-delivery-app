package com.mehdikamar.fooddelivery.di.builders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mehdikamar.fooddelivery.di.ViewModelFactory
import com.mehdikamar.fooddelivery.di.ViewModelKey
import com.mehdikamar.fooddelivery.ui.main.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}