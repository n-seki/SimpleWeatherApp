package seki.com.simpleweatherapp.weather.di

import dagger.Component
import seki.com.simpleweatherapp.weather.ui.detail.WeatherDetailFragment
import seki.com.simpleweatherapp.weather.ui.list.WeatherListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(WeatherApiModule::class, ViewModelModule::class))
internal interface AppComponent {
  fun inject(fragment: WeatherDetailFragment)
  fun inject(fragment: WeatherListFragment)
}

