package seki.com.simpleweatherapp.weather.ui.list

import android.arch.lifecycle.*
import seki.com.simpleweatherapp.weather.Weather
import seki.com.simpleweatherapp.weather.domain.ResponseWrapper
import seki.com.simpleweatherapp.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherListViewModel @Inject constructor(private val repo: WeatherRepository): ViewModel() {

    private val cityIdList = MutableLiveData<List<String>>()
    val weatherList: LiveData<List<ResponseWrapper<Weather>>>

    init {
        weatherList = Transformations.switchMap(cityIdList, this::loadWeatherList)
    }

    private fun loadWeatherList(cities: List<String>): LiveData<List<ResponseWrapper<Weather>>> {
        val resultListDataList = MutableLiveData<List<ResponseWrapper<Weather>>>()
        val mediator: MediatorLiveData<ResponseWrapper<Weather>> = MediatorLiveData()
        val weathers: MutableList<ResponseWrapper<Weather>> = mutableListOf()

        if (cities.isEmpty()) {
            resultListDataList.postValue(listOf())
            return resultListDataList
        }

        load(cities, mediator, weathers, resultListDataList)
        return resultListDataList
    }

    private fun load(cities: List<String>,
                     mediator: MediatorLiveData<ResponseWrapper<Weather>>,
                     weathers: MutableList<ResponseWrapper<Weather>>,
                     resultListDataList: MutableLiveData<List<ResponseWrapper<Weather>>>) {

        val city = cities.first()
        val weatherResponse = repo.getSingleWeather(city)
        mediator.observeForever { }
        mediator.addSource(weatherResponse) { response -> nextLoadOrPost(response, cities.drop(1), mediator, weathers, resultListDataList) }
    }

    private fun nextLoadOrPost(weatherResponse: ResponseWrapper<Weather>?,
                               cities: List<String>,
                               mediator: MediatorLiveData<ResponseWrapper<Weather>>,
                               weathers: MutableList<ResponseWrapper<Weather>>,
                               resultListDataList: MutableLiveData<List<ResponseWrapper<Weather>>>) {

        if (weatherResponse == null) {
            resultListDataList.postValue(weathers)
            return
        }

        weathers.add(weatherResponse)

        if (cities.isEmpty()) {
            resultListDataList.postValue(weathers)
            return
        }

        load(cities, mediator, weathers, resultListDataList)
    }

    fun update() {
        repo.getSelectedCityId(object : WeatherRepository.LoadSelectedCityCallback {
            override fun loadSelectedCity(selectedCityIdList: List<String>) {
                cityIdList.postValue(selectedCityIdList)
            }
        })
    }

    fun clear() {
        repo.clearLocation(object : WeatherRepository.CompleteClearLocation {
            override fun onClearLocation() {
                cityIdList.postValue(listOf())
            }
        })
    }

    fun deleteCity(cityId: String) {
        repo.deleteLocation(cityId, object : WeatherRepository.LoadSelectedCityCallback {
            override fun loadSelectedCity(selectedCityIdList: List<String>) {
                cityIdList.postValue(selectedCityIdList)
            }
        })
    }
}