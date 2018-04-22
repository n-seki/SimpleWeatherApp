package seki.com.simpleweatherapp.weather.domain

data class ResponseWrapper<out T>(val result : T? = null, val error: Throwable? = null) {
    fun isSuccess() = result != null
}