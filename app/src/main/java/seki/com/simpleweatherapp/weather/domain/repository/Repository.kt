package seki.com.simpleweatherapp.weather.domain.repository

import android.arch.lifecycle.LiveData
import seki.com.simpleweatherapp.weather.Weather
import seki.com.simpleweatherapp.weather.domain.ResponseWrapper
import seki.com.simpleweatherapp.weather.domain.db.Location

interface Repository {
    fun getSingleWeather(city: String): LiveData<ResponseWrapper<Weather>>
    fun getAreaData(): LiveData<List<Location>>
}