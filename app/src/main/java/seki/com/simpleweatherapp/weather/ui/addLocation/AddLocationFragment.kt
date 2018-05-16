package seki.com.simpleweatherapp.weather.ui.addLocation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleExpandableListAdapter
import seki.com.simpleweatherapp.R
import seki.com.simpleweatherapp.databinding.FragmentLocationListBinding
import seki.com.simpleweatherapp.weather.WeatherApplication
import seki.com.simpleweatherapp.weather.domain.db.Location
import seki.com.simpleweatherapp.weather.util.ViewModelFactory
import javax.inject.Inject

class AddLocationFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AddLocationViewModel::class.java) }

    private lateinit var binder: FragmentLocationListBinding

    companion object {
        fun getInstance() = AddLocationFragment()
        const val TAG = "add location"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (context.applicationContext as WeatherApplication).getAppComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = DataBindingUtil.inflate(inflater!!, R.layout.fragment_location_list, container, false)
        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getLocationList().observe(this, Observer { showLocationList(it) })
    }

    private fun showLocationList(list: List<Location>?) {
        list?.run { binder.locationList.setAdapter(makeExpandableListAdapter(this)) }
    }

    private fun makeExpandableListAdapter(data: List<Location>): SimpleExpandableListAdapter {
        return SimpleExpandableListAdapter(
                context,
                data.makeExpandableParentData(),
                android.R.layout.simple_expandable_list_item_1,
                arrayOf("prefName"),
                intArrayOf(android.R.id.text1),
                data.makeExpandableChildData(),
                android.R.layout.simple_expandable_list_item_1,
                arrayOf("cityName"),
                intArrayOf(android.R.id.text1)
        )
    }

    private fun List<Location>.makeExpandableParentData(): List<Map<String, String>> {
        val prefList = this.distinctBy { it.prefName }
        val parentList = mutableListOf<Map<String, String>>()
        for (location in prefList) {
            val map = mapOf("prefName" to location.prefName)
            parentList += map
        }
        return parentList
    }

    private fun List<Location>.makeExpandableChildData(): List<List<Map<String, String>>> {
        val cityMap = this.groupBy { it.prefName }
        val childList = mutableListOf<List<Map<String, String>>>()
        for (cityList in cityMap.values) {
            val oneChildList = mutableListOf<Map<String, String>>()
            for (city in cityList) {
                val map = mapOf("cityName" to city.cityName)
                oneChildList += map
            }
            childList += oneChildList
        }
        return childList
    }
}