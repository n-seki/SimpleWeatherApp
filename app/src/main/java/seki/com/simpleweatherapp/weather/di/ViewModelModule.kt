package seki.com.simpleweatherapp.weather.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import seki.com.simpleweatherapp.weather.ui.addLocation.AddLocationViewModel
import seki.com.simpleweatherapp.weather.ui.detail.WeatherDetailViewModel
import seki.com.simpleweatherapp.weather.ui.list.WeatherListViewModel
import seki.com.simpleweatherapp.weather.util.ViewModelFactory

@Module internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WeatherDetailViewModel::class)
    abstract fun bindWeatherDetailViewModel(viewModel: WeatherDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherListViewModel::class)
    abstract fun bindWeatherListViewModel(viewModel: WeatherListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddLocationViewModel::class)
    abstract fun bindAddLocationViewModel(viewModel: AddLocationViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}