package seki.com.simpleweatherapp.weather.domain.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Location(
        @PrimaryKey val id: String,
        @ColumnInfo(name="pref_name") val prefName: String,
        @ColumnInfo(name="city_name") val cityName: String,
        @ColumnInfo(name="selected") var selected: Int
)