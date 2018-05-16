package seki.com.simpleweatherapp.weather.domain.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import seki.com.simpleweatherapp.weather.Weather
import seki.com.simpleweatherapp.weather.domain.entity.WeatherEntity
import seki.com.simpleweatherapp.weather.api.WeatherService
import seki.com.simpleweatherapp.weather.domain.ResponseWrapper
import seki.com.simpleweatherapp.weather.domain.database.AppDataBase
import seki.com.simpleweatherapp.weather.domain.db.Location
import seki.com.simpleweatherapp.weather.domain.mapper.WeatherEntityMapper
import seki.com.simpleweatherapp.weather.util.Locations
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val service: WeatherService, private val mapper: WeatherEntityMapper, private val db: AppDataBase)
    : Repository {

    val executor: ExecutorService = Executors.newCachedThreadPool()

    override fun getSingleWeather(city: String): LiveData<ResponseWrapper<Weather>> {
        val data = MutableLiveData<ResponseWrapper<Weather>>()
        service.singleWeather(city).enqueue(object : Callback<WeatherEntity> {
            override fun onResponse(call: Call<WeatherEntity>, response: Response<WeatherEntity>) {
                val weather = mapper.convert(response.body()!!)
                data.postValue(ResponseWrapper(result = weather, error = null))
            }

            override fun onFailure(call: Call<WeatherEntity>, t: Throwable) {
                data.postValue(ResponseWrapper(result = null, error = t))
            }

        })
        return data
    }

    override fun storeLocation() {
        val data: MutableLiveData<List<Location>> = MutableLiveData()
        service.getAreaXml().enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                data.postValue(emptyList())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val xml = response.body()?.string() ?: throw IllegalStateException("body is null")
                val result = Locations.expandLocationXml(xml)
                executor.submit { db.locationDao().insert(result) }
            }

        })
    }

    override fun getLocation(): LiveData<List<Location>> {
        val data: MutableLiveData<List<Location>> = MutableLiveData()
        executor.execute {
            val locations = db.locationDao().selectAllLocation()
            data.postValue(locations)
        }
        return data
    }
}