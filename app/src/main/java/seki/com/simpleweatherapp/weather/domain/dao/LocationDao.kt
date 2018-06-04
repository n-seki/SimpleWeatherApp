package seki.com.simpleweatherapp.weather.domain.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import seki.com.simpleweatherapp.weather.domain.db.Location

@Dao
interface LocationDao {

    @Insert
    fun insert(locations: List<Location>)

    @Query("UPDATE location set selected = 1 WHERE id = :cityId")
    fun changeToSelected(cityId:String)

    @Query("SELECT id FROM location WHERE selected = 1")
    fun getSelectedCityId(): List<String>

    @Query("SELECT count(*) FROM location")
    fun getLocationCount(): Int

    @Query("SELECT * FROM location ORDER BY id")
    fun selectAllLocation(): LiveData<List<Location>>

    @Query("UPDATE location SET selected = 0")
    fun clearAllSelected()
}
