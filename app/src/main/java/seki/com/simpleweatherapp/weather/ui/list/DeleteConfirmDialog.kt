package seki.com.simpleweatherapp.weather.ui.list

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import seki.com.simpleweatherapp.R
import seki.com.simpleweatherapp.weather.Weather

class DeleteConfirmDialog: DialogFragment() {

    lateinit var viewModel: WeatherListViewModel

    companion object {
        const val CITY_NAME = "city_name"
        const val CITY_ID = "city_id"

        fun newInstance(weather: Weather): DeleteConfirmDialog {
            val data: Bundle = Bundle().apply {
                putString(CITY_NAME, weather.local.city)
                putString(CITY_ID, weather.local.id)
            }

            return DeleteConfirmDialog().apply {
                arguments = data
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        viewModel = (context as WeatherListActivity).getWeatherListViewModel()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cityId: String = arguments.getString(CITY_ID)
        val cityName: String = arguments.getString(CITY_NAME)

        return AlertDialog.Builder(activity)
                .setMessage(context.getString(R.string.delete).format(cityName))
                .setPositiveButton(R.string.ok, { _ , _  ->  viewModel.deleteCity(cityId) })
                .setNegativeButton(R.string.cancel, { _, _ -> dismiss() })
                .create()
    }
}