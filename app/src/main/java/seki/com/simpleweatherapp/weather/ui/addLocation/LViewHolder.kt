package seki.com.simpleweatherapp.weather.ui.addLocation

import android.support.v7.widget.RecyclerView
import android.view.View
import seki.com.simpleweatherapp.databinding.LayoutLocationListItemBinding

class LViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val binder by lazy { LayoutLocationListItemBinding.bind(itemView) }
}