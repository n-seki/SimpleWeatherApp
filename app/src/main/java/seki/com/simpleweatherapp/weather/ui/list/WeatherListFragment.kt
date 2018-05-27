package seki.com.simpleweatherapp.weather.ui.list

 import android.arch.lifecycle.Observer
 import android.arch.lifecycle.ViewModelProviders
 import android.databinding.DataBindingUtil
 import android.os.Bundle
 import android.support.v4.app.Fragment
 import android.support.v7.widget.DividerItemDecoration
 import android.support.v7.widget.LinearLayoutManager
 import android.util.Log
 import android.view.LayoutInflater
 import android.view.View
 import android.view.ViewGroup
 import seki.com.simpleweatherapp.R
 import seki.com.simpleweatherapp.databinding.FragmentWeatherListBinding
 import seki.com.simpleweatherapp.weather.MainActivity
 import seki.com.simpleweatherapp.weather.Weather
 import seki.com.simpleweatherapp.weather.WeatherApplication
 import seki.com.simpleweatherapp.weather.domain.ResponseWrapper
 import seki.com.simpleweatherapp.weather.domain.db.Location
 import seki.com.simpleweatherapp.weather.util.Locations
 import seki.com.simpleweatherapp.weather.util.ViewModelFactory
 import javax.inject.Inject

class WeatherListFragment: Fragment(), WeatherListAdapter.ItemClickListener {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(WeatherListViewModel::class.java) }
    lateinit var binding: FragmentWeatherListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (context.applicationContext as WeatherApplication).getAppComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_list, container, false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val deco = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.weatherList.addItemDecoration(deco)
        binding.weatherList.layoutManager = layoutManager
        binding.weatherList.adapter = WeatherListAdapter(context, listOf(), this)
        return this.binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.weatherList.observe(this, Observer<List<ResponseWrapper<Weather>>>(this::showWeatherList))
        viewModel.storeLocation()

        val cities = listOf("110010", "130010", "200010", "040010")
        viewModel.cityList.postValue(cities)
    }

    private fun showWeatherList(list: List<ResponseWrapper<Weather>>?) {
        list?.let { responseList ->
            val weathers = responseList
                    .filter { response -> response.isSuccess() }
                    .map { response -> response.result ?: throw IllegalStateException("Response result is null!") }

            (binding.weatherList.adapter as WeatherListAdapter).data = weathers
        }
    }

    override fun onItemClick(weather: Weather) {
        val cityId: String = Locations.locals[weather.local.city] ?: throw IllegalStateException(weather.local.city)
        (activity as MainActivity).showDetailScreen(cityId)
    }

    companion object {
        const val TAG = "list"

        fun getInstance() = WeatherListFragment()
    }
}