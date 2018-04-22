package seki.com.simpleweatherapp.weather.di

import dagger.Module
import dagger.Provides
import seki.com.simpleweatherapp.weather.api.WeatherService
import seki.com.simpleweatherapp.weather.domain.repository.Repository
import seki.com.simpleweatherapp.weather.domain.mapper.WeatherEntityMapper
import seki.com.simpleweatherapp.weather.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
class WeatherApiModule {

    @Provides
    @Singleton
    fun getWeatherService() = WeatherService.getService()

    @Provides
    @Singleton
    fun getWeatherRepository(service: WeatherService, mapper: WeatherEntityMapper): Repository = WeatherRepository(service, mapper)

    @Provides
    @Singleton
    fun getEntityMapper() = WeatherEntityMapper()
}