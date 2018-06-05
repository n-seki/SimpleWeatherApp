package seki.com.simpleweatherapp.weather.util

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import seki.com.simpleweatherapp.weather.Weather
import seki.com.simpleweatherapp.weather.ui.list.WeatherListAdapter

@BindingAdapter("android:listData")
fun setListData(listView: RecyclerView, data: List<Weather>) {
    val adapter = listView.adapter as WeatherListAdapter
    adapter.data = data
}