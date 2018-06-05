package seki.com.simpleweatherapp.weather.ui.list

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import seki.com.simpleweatherapp.R

class ClearConfirmDialog: DialogFragment() {

    lateinit var viewModel: WeatherListViewModel

    companion object {
        fun newInstance() = ClearConfirmDialog()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        viewModel = (context as WeatherListActivity).getWeatherListViewModel()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setMessage(context.getString(R.string.clear_all_city))
                .setPositiveButton(R.string.ok, { _, _ -> viewModel.clear() })
                .setNegativeButton(R.string.cancel, { _ , _ -> dismiss() })
                .create()
    }
}