package seki.com.simpleweatherapp.weather.di

import dagger.Component
import seki.com.simpleweatherapp.weather.ui.addLocation.AddLocationFragment
import seki.com.simpleweatherapp.weather.ui.detail.WeatherDetailFragment
import seki.com.simpleweatherapp.weather.ui.list.WeatherListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [(WeatherAppModule::class), (WeatherApiModule::class), (ViewModelModule::class)])
internal interface AppComponent {
  fun inject(fragment: WeatherDetailFragment)
  fun inject(fragment: WeatherListFragment)
  fun inject(fragment: AddLocationFragment)
}

