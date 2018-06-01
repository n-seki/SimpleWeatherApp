package seki.com.simpleweatherapp.weather.ui.addLocation

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.SimpleExpandableListAdapter
import seki.com.simpleweatherapp.R
import seki.com.simpleweatherapp.databinding.FragmentLocationListBinding
import seki.com.simpleweatherapp.weather.domain.db.Location

class AddLocationFragment: Fragment(), AddLocationConfirmDialog.DialogClickListener {

    private lateinit var locations: Map<String, Location>

    private lateinit var binder: FragmentLocationListBinding

    private lateinit var viewModel: AddLocationViewModel

    companion object {
        fun getInstance() = AddLocationFragment()
        const val TAG = "add location"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = DataBindingUtil.inflate(inflater!!, R.layout.fragment_location_list, container, false)
        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = (activity as AddLocationActivity).getAddLocationViewModel()
        viewModel.getLocationList().observe(this, Observer { showLocationList(it) })
    }

    private fun showLocationList(list: List<Location>?) {
        list?.run {
            locations = this.associateBy { it.id }
            binder.locationList.setAdapter(makeExpandableListAdapter(this))
            binder.locationList.setOnChildClickListener { parent, _, groupPosition, childPosition, _ ->
                parent?.run {onClickCity(this, groupPosition, childPosition)}
                true
            }
        }
    }

    private fun onClickCity(listView: ExpandableListView, groupPosition: Int, childPosition: Int):Boolean {
        val adapter = listView.expandableListAdapter
        val childItem = adapter.getChild(groupPosition, childPosition) as Map<*, *>
        val cityName = childItem["cityName"] as String?
        val cityId = childItem["cityId"] as String?
        if (cityId != null && cityName != null) {
            showDialog(cityId, cityName)
        }
        return true
    }

    private fun showDialog(cityId: String, cityName: String) {
        AddLocationConfirmDialog.newInstance(cityId, cityName)
                .show(childFragmentManager, "confirm")
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
                val map = mapOf("cityName" to city.cityName, "cityId" to city.id)
                oneChildList += map
            }
            childList += oneChildList
        }
        return childList
    }

    override fun onClickConfirm(cityId: String) {
        viewModel.addLocation(cityId)
    }
}