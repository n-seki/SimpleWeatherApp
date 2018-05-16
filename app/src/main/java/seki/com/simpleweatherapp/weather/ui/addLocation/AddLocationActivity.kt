package seki.com.simpleweatherapp.weather.ui.addLocation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import seki.com.simpleweatherapp.R
import seki.com.simpleweatherapp.weather.ui.detail.WeatherDetailActivity

class AddLocationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)

        if (savedInstanceState == null) {
            val fragment = AddLocationFragment.getInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, AddLocationFragment.TAG)
                    .commit()
        }
    }

    companion object {
        fun intent(context: Context) =
                Intent(context, AddLocationActivity::class.java)
    }
}