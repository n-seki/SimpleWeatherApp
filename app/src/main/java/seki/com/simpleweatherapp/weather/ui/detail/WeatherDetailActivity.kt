package seki.com.simpleweatherapp.weather.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import seki.com.simpleweatherapp.R

class WeatherDetailActivity: AppCompatActivity() {

    private val mCityId: String by lazy { intent.getStringExtra(CITY_ID) }

    companion object {
        private const val CITY_ID = "city_id"

        fun intent(context: Context, cityId: String) =
                Intent(context, WeatherDetailActivity::class.java).putExtra(CITY_ID, cityId)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_weather)

        initToolbar()

        if (savedInstanceState == null) {
            val fragment = WeatherDetailFragment.getInstance(mCityId)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, WeatherDetailFragment.TAG)
                    .commit()
        }
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                android.R.id.home -> {
                    finish()
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
}