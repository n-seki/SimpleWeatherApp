package seki.com.simpleweatherapp.weather.ui.addLocation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import seki.com.simpleweatherapp.R
import seki.com.simpleweatherapp.weather.domain.db.Location

class LocationListAdapter(private val mContext: Context, private val mData: List<Location>): RecyclerView.Adapter<LViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_location_list_item, parent, false)
        return LViewHolder(itemView)
    }

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(holder: LViewHolder?, position: Int) {
        val location = mData[position]
        holder!!.binder.location = location
    }
}