package seki.com.simpleweatherapp.weather.domain.mapper

import seki.com.simpleweatherapp.weather.Location
import seki.com.simpleweatherapp.weather.Weather
import seki.com.simpleweatherapp.weather.domain.entity.WeatherEntity


class WeatherEntityMapper {
    fun convert(entity: WeatherEntity): Weather {
        val location = Location(entity.location.area, entity.location.prefecture, entity.location.city)
        return Weather(entity.forecasts[0].telop, entity.description.text, location)
    }
}