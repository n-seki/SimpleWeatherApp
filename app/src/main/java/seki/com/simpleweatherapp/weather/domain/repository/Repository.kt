package seki.com.simpleweatherapp.weather.domain.repository

import android.arch.lifecycle.LiveData
import seki.com.simpleweatherapp.weather.Weather
import seki.com.simpleweatherapp.weather.domain.ResponseWrapper
import seki.com.simpleweatherapp.weather.domain.db.Location

interface Repository {
    fun getSingleWeather(cityId: String): LiveData<ResponseWrapper<Weather>>
    fun storeLocation()
    fun getLocation(): LiveData<List<Location>>
    fun addLocation(location: Location, callback: CompleteAddLocationCallback)
    fun getSelectedCityId(callback: LoadSelectedCityCallback)

    interface LoadSelectedCityCallback {
        fun loadSelectedCity(cityList: List<String>)
    }

    interface CompleteAddLocationCallback {
        fun onCompleteAddLocation()
    }
}