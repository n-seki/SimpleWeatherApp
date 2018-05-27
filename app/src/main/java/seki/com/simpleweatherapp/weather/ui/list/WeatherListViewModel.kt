package seki.com.simpleweatherapp.weather.ui.list

import android.arch.lifecycle.*
import seki.com.simpleweatherapp.weather.Weather
import seki.com.simpleweatherapp.weather.domain.ResponseWrapper
import seki.com.simpleweatherapp.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherListViewModel @Inject constructor(private val repo: WeatherRepository): ViewModel() {
    val cityList = MutableLiveData<List<String>>()
    val weatherList: LiveData<List<ResponseWrapper<Weather>>>

    init {
        weatherList = Transformations.switchMap(cityList, this::loadWeatherList)
    }

    private fun loadWeatherList(cities: List<String>): LiveData<List<ResponseWrapper<Weather>>> {
        val resultListDataList = MutableLiveData<List<ResponseWrapper<Weather>>>()
        val mediator: MediatorLiveData<ResponseWrapper<Weather>> = MediatorLiveData()
        val weathers: MutableList<ResponseWrapper<Weather>> = mutableListOf()

        if (cities.isNotEmpty()) {
            load(cities, mediator, weathers, resultListDataList, 0)
        }

        return resultListDataList
    }

    private fun load(cities: List<String>,
                     mediator: MediatorLiveData<ResponseWrapper<Weather>>,
                     weathers: MutableList<ResponseWrapper<Weather>>,
                     resultListDataList: MutableLiveData<List<ResponseWrapper<Weather>>>,
                     position: Int) {

        val city = cities[position]
        val weatherResponse = repo.getSingleWeather(city)
        mediator.observeForever { }
        mediator.addSource(weatherResponse) { response -> nextLoadOrPost(response, position, cities, mediator, weathers, resultListDataList) }
    }

    private fun nextLoadOrPost(weatherResponse: ResponseWrapper<Weather>?,
                               position: Int,
                               cities: List<String>,
                               mediator: MediatorLiveData<ResponseWrapper<Weather>>,
                               weathers: MutableList<ResponseWrapper<Weather>>,
                               resultListDataList: MutableLiveData<List<ResponseWrapper<Weather>>>) {

        if (weatherResponse == null) {
            resultListDataList.postValue(weathers)
            return
        }

        weathers.add(weatherResponse)

        if (position + 1 == cities.size) {
            resultListDataList.postValue(weathers)
            return
        }

        load(cities, mediator, weathers, resultListDataList, position + 1)
    }

    fun storeLocation() {
        repo.storeLocation()
    }
}