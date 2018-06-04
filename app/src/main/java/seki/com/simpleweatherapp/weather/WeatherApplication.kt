package seki.com.simpleweatherapp.weather

import android.app.Application
import seki.com.simpleweatherapp.weather.di.AppComponent
import seki.com.simpleweatherapp.weather.di.DaggerAppComponent
import seki.com.simpleweatherapp.weather.di.WeatherApiModule
import seki.com.simpleweatherapp.weather.di.WeatherAppModule

class WeatherApplication: Application() {

    val applicationComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .weatherApiModule(WeatherApiModule())
            .weatherAppModule(WeatherAppModule(this))
            .build()
    }
}