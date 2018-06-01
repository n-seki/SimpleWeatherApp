package seki.com.simpleweatherapp.weather.ui.addLocation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import seki.com.simpleweatherapp.weather.di.WeatherAppModule
import seki.com.simpleweatherapp.weather.domain.db.Location
import seki.com.simpleweatherapp.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class AddLocationViewModel @Inject constructor(private val repo: WeatherRepository): ViewModel() {

    enum class Status {
        NO_CHANGE,
        UPDATE
    }

    private var locations: LiveData<List<Location>> = MutableLiveData()
    val status: MutableLiveData<Status> = MutableLiveData()

    fun getLocationList(): LiveData<List<Location>> {
        return repo.locationListLiveData
    }

    fun addLocation(cityId: String) {
        val city = locations.value?.associateBy { it.id }?.get(cityId) ?: return
        city.selected = 1
        repo.addLocation(city, object : WeatherRepository.CompleteAddLocationCallback {
            override fun onCompleteAddLocation() {
                status.postValue(Status.UPDATE)
            }
        })
    }
}