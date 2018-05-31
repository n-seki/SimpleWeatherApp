package seki.com.simpleweatherapp.weather.ui.addLocation

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import seki.com.simpleweatherapp.R

class AddLocationConfirmDialog: DialogFragment() {

    lateinit var listener: DialogClickListener

    interface DialogClickListener {
        fun onClickConfirm(cityId: String)
    }

    companion object {
        const val CITY_ID = "city_id"
        const val CITY_NAME = "city_name"

        fun newInstance(cityId: String, cityName: String): DialogFragment  {
            val data: Bundle = Bundle().apply {
                putString(CITY_ID, cityId)
                putString(CITY_NAME, cityName)
            }

            return AddLocationConfirmDialog().apply {
                arguments = data
            }
        }
    }

    override fun onAttach(childFragment: Context?) {
        super.onAttach(context)
        listener = (parentFragment as? DialogClickListener)?:
                throw IllegalStateException("fragment not implement Listener")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cityId = arguments.getString(CITY_ID)
        val cityName = arguments.getString(CITY_NAME)

        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                .setMessage("${cityName}を追加しますか？")
                .setPositiveButton(R.string.ok, { _: DialogInterface?, _: Int ->  listener.onClickConfirm(cityId) })
                .setNegativeButton(R.string.cancel, { dialog: DialogInterface?, _: Int -> dialog?.dismiss() })

            return builder.create()
    }
}