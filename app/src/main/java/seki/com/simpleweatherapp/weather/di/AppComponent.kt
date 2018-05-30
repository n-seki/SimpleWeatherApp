package seki.com.simpleweatherapp.weather.di

import dagger.Component
import seki.com.simpleweatherapp.weather.ui.list.MainActivity
import seki.com.simpleweatherapp.weather.ui.addLocation.AddLocationActivity
import seki.com.simpleweatherapp.weather.ui.detail.WeatherDetailFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [(WeatherAppModule::class), (WeatherApiModule::class), (ViewModelModule::class)])
internal interface AppComponent {
  fun inject(fragment: WeatherDetailFragment)
  fun inject(activity: MainActivity)
  fun inject(activity: AddLocationActivity)
}

