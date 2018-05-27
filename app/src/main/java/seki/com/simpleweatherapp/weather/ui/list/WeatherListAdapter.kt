package seki.com.simpleweatherapp.weather.ui.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import seki.com.simpleweatherapp.R
import seki.com.simpleweatherapp.weather.Weather

class WeatherListAdapter(private val context: Context, data: List<Weather>, private val listener: ItemClickListener): RecyclerView.Adapter<WViewHolder>() {

    var data: List<Weather> = data
        set(weathers) {
            field = weathers
            notifyDataSetChanged()
        }

    interface ItemClickListener {
        fun onItemClick(weather: Weather)
    }

    override fun onBindViewHolder(holder: WViewHolder?, position: Int) {
        holder!!.binder.weather = data[position]
        holder.binder.root.setOnClickListener({ listener.onItemClick(data[position] )})
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WViewHolder {
        val view = LayoutInflater
                .from(context)
                .inflate(R.layout.layout_weather_list_item, parent, false)
        return WViewHolder(view)
    }
}