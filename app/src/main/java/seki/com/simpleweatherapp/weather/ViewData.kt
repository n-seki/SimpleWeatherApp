package seki.com.simpleweatherapp.weather

data class Weather(val weather: String, val description: String, val local: Location)
data class Location(val area: String, val pref: String, val city: String)