package seki.com.simpleweatherapp.weather.ui.list

 import android.arch.lifecycle.Observer
 import android.databinding.DataBindingUtil
 import android.os.Bundle
 import android.support.design.widget.FloatingActionButton
 import android.support.v4.app.Fragment
 import android.support.v7.widget.DividerItemDecoration
 import android.support.v7.widget.LinearLayoutManager
 import android.view.LayoutInflater
 import android.view.View
 import android.view.ViewGroup
 import seki.com.simpleweatherapp.R
 import seki.com.simpleweatherapp.databinding.FragmentWeatherListBinding
 import seki.com.simpleweatherapp.weather.Weather
 import seki.com.simpleweatherapp.weather.domain.ResponseWrapper

class WeatherListFragment: Fragment(), WeatherListAdapter.ItemClickListener {

    lateinit var viewModel: WeatherListViewModel
    lateinit var binding: FragmentWeatherListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_list, container, false)
        binding.weatherList.apply {
            layoutManager = LinearLayoutManager(this@WeatherListFragment.context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this@WeatherListFragment.context, DividerItemDecoration.VERTICAL))
            adapter = WeatherListAdapter(listOf(), this@WeatherListFragment)
        }

        return this.binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupFab()

        viewModel = (activity as MainActivity).getWeatherListViewModel()
        viewModel.weatherList.observe(this, Observer<List<ResponseWrapper<Weather>>>(this::showWeatherList))
    }

    private fun setupFab() {
        activity.findViewById<FloatingActionButton>(R.id.fab).apply {
            setOnClickListener { showAddLocationScreen() }
        }
    }

    private fun showWeatherList(list: List<ResponseWrapper<Weather>>?) {
        list?.let { responseList ->
            val weathers = responseList
                    .filter { response -> response.isSuccess() }
                    .map { response -> response.result ?: throw IllegalStateException("Response result is null!") }

            (binding.weatherList.adapter as WeatherListAdapter).data = weathers
        }
    }

    private fun showAddLocationScreen() {
        (activity as MainActivity).showAddLocationActivity()
    }

    override fun onItemClick(weather: Weather) {
        (activity as MainActivity).showDetailScreen(weather.local.id)
    }

    companion object {
        const val TAG = "list"

        fun getInstance() = WeatherListFragment()
    }
}