package seki.com.simpleweatherapp.weather.ui.list

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment

class DeleteConfirmDialog: DialogFragment() {

    lateinit var listener: DeleteConfirmDialogListener

    interface DeleteConfirmDialogListener {
        fun onClickDeleteOk()
    }

    companion object {
        const val CITY_NAME = "city_name"

        fun newInstance(cityName: String): DeleteConfirmDialog {
            val data: Bundle = Bundle().apply {
                putString(CITY_NAME, cityName)
            }

            return DeleteConfirmDialog().apply {
                arguments = data
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = (parentFragment as? DeleteConfirmDialogListener) ?:
                throw IllegalStateException("Parent fragment does not implement Listener!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cityName: String = arguments.getString(CITY_NAME)

        return AlertDialog.Builder(activity)
                .setMessage("${cityName}を削除しますか？")
                .create()
    }
}