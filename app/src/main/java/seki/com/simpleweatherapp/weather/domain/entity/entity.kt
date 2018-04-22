package seki.com.simpleweatherapp.weather.domain.entity

import java.util.*

data class WeatherEntity(val location: LocationEntity, val description: DescriptionEntity, val forecasts: Array<ForecastsEntity>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeatherEntity

        if (location != other.location) return false
        if (description != other.description) return false
        if (!Arrays.equals(forecasts, other.forecasts)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = location.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + Arrays.hashCode(forecasts)
        return result
    }
}

data class LocationEntity(val area: String, val prefecture: String, val city: String)
data class DescriptionEntity(val text: String, val publicTime: String)
data class ForecastsEntity(val date: String, val telop: String, val image: ImageEntity, val temperature: TemperatureEntity)
data class ImageEntity(val url: String)
data class TemperatureEntity(val celsius: String)