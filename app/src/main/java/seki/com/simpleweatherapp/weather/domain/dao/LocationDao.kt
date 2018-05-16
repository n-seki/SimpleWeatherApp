package seki.com.simpleweatherapp.weather.domain.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import seki.com.simpleweatherapp.weather.domain.db.Location

@Dao
interface LocationDao {

    @Insert
    fun insert(locations: List<Location>)
}
