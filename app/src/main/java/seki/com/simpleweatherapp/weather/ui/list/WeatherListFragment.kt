package seki.com.simpleweatherapp.weather.ui.list

 import android.arch.lifecycle.Observer
 import android.databinding.DataBindingUtil
 import android.os.Bundle
 import android.support.design.widget.FloatingActionButton
 import android.support.v4.app.Fragment
 import android.support.v7.widget.DividerItemDecoration
 import android.support.v7.widget.LinearLayoutManager
 import android.view.*
 import kotlinx.android.synthetic.main.fragment_weather_list.view.*
 import seki.com.simpleweatherapp.R
 import seki.com.simpleweatherapp.databinding.FragmentWeatherListBinding
 import seki.com.simpleweatherapp.weather.Weather
 import seki.com.simpleweatherapp.weather.domain.ResponseWrapper

class WeatherListFragment:
        Fragment(),
        WeatherListAdapter.ItemClickListener {

    private lateinit var viewModel: WeatherListViewModel
    private lateinit var binding: FragmentWeatherListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_list, container, false)
        binding.weatherList.apply {
            layoutManager = LinearLayoutManager(this@WeatherListFragment.context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this@WeatherListFragment.context, DividerItemDecoration.VERTICAL))
            adapter = WeatherListAdapter(this@WeatherListFragment)
        }
        binding.weatherListData = listOf()

        return this.binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupFab()

        viewModel = (activity as WeatherListActivity).getWeatherListViewModel()
        viewModel.weatherList.observe(this, Observer<List<ResponseWrapper<Weather>>>(this::showWeatherList))
        viewModel.update()
    }

    private fun setupFab() {
        activity.findViewById<FloatingActionButton>(R.id.fab).apply {
            setOnClickListener { showAddLocationScreen() }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_weather_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when(item.itemId) {
                R.id.action_clear -> {
                    ClearConfirmDialog.newInstance().show(childFragmentManager, "clear_confirm")
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    private fun showWeatherList(list: List<ResponseWrapper<Weather>>?) {
        if (list == null || list.isEmpty()) {
            binding.weatherListData = listOf()
            return
        }

        list.let { responseList ->
            val weathers = responseList
                    .filter { response -> response.isSuccess() }
                    .map { response -> response.result ?: throw IllegalStateException("Response result is null!") }

            binding.weatherListData = weathers
        }
    }

    private fun showAddLocationScreen() {
        (activity as WeatherListActivity).showAddLocationActivity()
    }

    override fun onItemClick(weather: Weather) {
        (activity as WeatherListActivity).showDetailScreen(weather.local.id)
    }

    override fun onItemLongLick(weather: Weather): Boolean {
        DeleteConfirmDialog.newInstance(weather).show(childFragmentManager, "delete_city")
        return true
    }

    companion object {
        const val TAG = "list"

        fun getInstance() = WeatherListFragment()
    }
}