package seki.com.simpleweatherapp.weather.ui.list

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import seki.com.simpleweatherapp.R

class ClearConfirmDialog: DialogFragment() {

    lateinit var listener: ClearConfrimListener

    interface ClearConfrimListener {
        fun onClickClearOk()
    }

    companion object {
        fun newInstance() = ClearConfirmDialog()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = (activity as? ClearConfrimListener) ?:
                throw IllegalStateException("activity not implement Listener")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setMessage("登録した地域をすべてクリアしますか？")
                .setPositiveButton(R.string.ok, { _, _ -> listener.onClickClearOk() })
                .setNegativeButton(R.string.cancel, { _ , _ -> dismiss() })
                .create()
    }
}