package seki.com.simpleweatherapp.weather.domain.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import seki.com.simpleweatherapp.weather.domain.dao.LocationDao
import seki.com.simpleweatherapp.weather.domain.db.Location

@Database(entities = [(Location::class)], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}