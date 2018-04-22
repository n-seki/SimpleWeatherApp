package seki.com.simpleweatherapp.weather.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import seki.com.simpleweatherapp.weather.domain.entity.WeatherEntity

interface WeatherService {
    @GET("webservice/json/v1")
    fun singleWeather(@Query("city") city: String): Call<WeatherEntity>

    @GET("rss/primary_area.xml")
    fun getAreaXml(): Call<ResponseBody>

    companion object {
        private const val BASE_URL = "http://weather.livedoor.com/forecast/"

        fun getService(): WeatherService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(WeatherService::class.java)
        }
    }
}