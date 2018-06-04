package seki.com.simpleweatherapp.weather.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import seki.com.simpleweatherapp.R
import seki.com.simpleweatherapp.databinding.FragmentWeatherDetailBinding
import seki.com.simpleweatherapp.weather.Weather
import seki.com.simpleweatherapp.weather.WeatherApplication
import seki.com.simpleweatherapp.weather.di.DaggerAppComponent
import seki.com.simpleweatherapp.weather.di.WeatherApiModule
import seki.com.simpleweatherapp.weather.domain.ResponseWrapper
import seki.com.simpleweatherapp.weather.util.ViewModelFactory
import javax.inject.Inject

class WeatherDetailFragment: Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val weatherDetailViewModel: WeatherDetailViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(WeatherDetailViewModel::class.java) }
    private lateinit var binding: FragmentWeatherDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (context.applicationContext as WeatherApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_detail, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.weatherDetailViewModel.weather.observe(this, Observer<ResponseWrapper<Weather>> { catchResponse(it) })
        val city = arguments.getString(CITY)
        this.weatherDetailViewModel.setCity(city)
    }

    private fun catchResponse(response: ResponseWrapper<Weather>?) {
        response!! //check null
        if (response.isSuccess())
            updateView(response.result!!)
        else
            showFailure(response.error!!)
    }

    private fun updateView(weather: Weather) {
        binding.weather = weather
    }

    private fun showFailure(error: Throwable) {
        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
    }

    companion object {

        const val CITY: String = "city"
        const val TAG = "detail"

        fun getInstance(city: String): Fragment {
            val fragment = WeatherDetailFragment()
            val bundle = Bundle()
            bundle.putString(CITY, city)
            fragment.arguments = bundle
            return fragment
        }
    }

}