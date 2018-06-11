package seki.com.simpleweatherapp.weather.ui.addLocation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import seki.com.simpleweatherapp.weather.domain.db.Location
import seki.com.simpleweatherapp.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class AddLocationViewModel @Inject constructor(private val repo: WeatherRepository): ViewModel() {

    enum class Status {
        NO_CHANGE,
        UPDATE
    }

    private val _status: MutableLiveData<Status> = MutableLiveData()
    val status: LiveData<Status> = _status

    fun getLocationList(): LiveData<List<Location>> {
        return repo.locationListLiveData
    }

    fun addLocation(cityId: String) {
        repo.addLocation(cityId, object : WeatherRepository.CompleteLocationSelectedStatusChanged {
            override fun onCompleteChanged() {
                _status.postValue(Status.UPDATE)
            }
        })
    }
}