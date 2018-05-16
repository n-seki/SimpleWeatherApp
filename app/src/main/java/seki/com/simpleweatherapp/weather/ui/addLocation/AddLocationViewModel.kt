package seki.com.simpleweatherapp.weather.ui.addLocation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import seki.com.simpleweatherapp.weather.domain.db.Location
import seki.com.simpleweatherapp.weather.domain.repository.Repository
import javax.inject.Inject

class AddLocationViewModel @Inject constructor(private val repo: Repository): ViewModel() {

    fun getLocationList(): LiveData<List<Location>> {
        return repo.getLocation()
    }
}