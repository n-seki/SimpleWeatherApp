package seki.com.simpleweatherapp.weather.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import seki.com.simpleweatherapp.R
import seki.com.simpleweatherapp.weather.WeatherApplication
import seki.com.simpleweatherapp.weather.ui.addLocation.AddLocationActivity
import seki.com.simpleweatherapp.weather.ui.detail.WeatherDetailActivity
import seki.com.simpleweatherapp.weather.util.ViewModelFactory
import javax.inject.Inject

class WeatherListActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(WeatherListViewModel::class.java) }

    private val mToolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (applicationContext as WeatherApplication).applicationComponent.inject(this)

        setSupportActionBar(mToolbar)

        if (savedInstanceState == null) {
            val fragment = WeatherListFragment.getInstance()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment, WeatherListFragment.TAG).commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ADD_LOCATION) {
            if (resultCode == AddLocationActivity.ADD_SUCCESS) {
                viewModel.update()
            }
        }
    }

    fun showDetailScreen(cityId: String) {
        val intent = WeatherDetailActivity.intent(this, cityId)
        startActivity(intent)
    }

    fun showAddLocationActivity() {
        val intent = AddLocationActivity.intent(this)
        startActivityForResult(intent, REQUEST_ADD_LOCATION)
    }

    fun getWeatherListViewModel() = viewModel

    companion object {
        const val REQUEST_ADD_LOCATION = 1
    }
}