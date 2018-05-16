package seki.com.simpleweatherapp.weather.domain.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import seki.com.simpleweatherapp.weather.domain.db.Location

@Dao
interface LocationDao {

    @Insert
    fun insert(locations: List<Location>)

    @Query("SELECT * FROM location ORDER BY id")
    fun selectAllLocation(): List<Location>
}
