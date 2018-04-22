package seki.com.simpleweatherapp.weather.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import seki.com.simpleweatherapp.weather.Weather
import seki.com.simpleweatherapp.weather.domain.ResponseWrapper
import seki.com.simpleweatherapp.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherDetailViewModel @Inject constructor(private val weatherRepo: WeatherRepository): ViewModel() {
    val weather: LiveData<ResponseWrapper<Weather>>
    private val cityData = MutableLiveData<String>()

    init {
        weather = Transformations.switchMap(cityData) {
            weatherRepo.getSingleWeather(it)
        }
    }

    fun setCity(city: String) {
        cityData.postValue(city)
    }

}