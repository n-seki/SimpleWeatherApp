package seki.com.simpleweatherapp.weather.domain.dao

import android.arch.persistence.room.*
import seki.com.simpleweatherapp.weather.domain.db.Location

@Dao
interface LocationDao {

    @Insert
    fun insert(locations: List<Location>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(location: Location)

    @Query("SELECT id FROM location WHERE selected = 1")
    fun getSelectedCityId(): List<String>

    @Query("SELECT * FROM location ORDER BY id")
    fun selectAllLocation(): List<Location>

    @Query("UPDATE location SET selected = 0")
    fun clearAllSelected()
}
