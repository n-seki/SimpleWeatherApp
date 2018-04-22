package seki.com.simpleweatherapp.weather.ui.list

import android.support.v7.widget.RecyclerView
import android.view.View
import seki.com.simpleweatherapp.databinding.LayoutWeatherListItemBinding

class WViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binder: LayoutWeatherListItemBinding by lazy { LayoutWeatherListItemBinding.bind(itemView) }
}