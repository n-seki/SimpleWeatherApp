package seki.com.simpleweatherapp.weather.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import seki.com.simpleweatherapp.weather.api.WeatherService
import seki.com.simpleweatherapp.weather.domain.database.AppDataBase
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
    fun getWeatherRepository(service: WeatherService, mapper: WeatherEntityMapper, dataBase: AppDataBase): Repository
            = WeatherRepository(service, mapper, dataBase)

    @Provides
    @Singleton
    fun getEntityMapper() = WeatherEntityMapper()

    @Provides
    @Singleton
    fun getDataBase(context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "weather_app_db").build()
    }
}