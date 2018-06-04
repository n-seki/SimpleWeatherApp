package seki.com.simpleweatherapp.weather.ui.addLocation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import seki.com.simpleweatherapp.R
import seki.com.simpleweatherapp.weather.WeatherApplication
import seki.com.simpleweatherapp.weather.ui.detail.WeatherDetailActivity
import seki.com.simpleweatherapp.weather.util.ViewModelFactory
import javax.inject.Inject

class AddLocationActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AddLocationViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)


        (applicationContext as WeatherApplication).applicationComponent.inject(this)

        if (savedInstanceState == null) {
            val fragment = AddLocationFragment.getInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, AddLocationFragment.TAG)
                    .commit()
        }

        viewModel.status.observe(this,  Observer { state -> changeState(state) })
    }

    private fun changeState(state: AddLocationViewModel.Status?) {
        when(state) {
            AddLocationViewModel.Status.UPDATE -> {
                setResult(ADD_SUCCESS)
                finish()
            }

            else -> return
        }
    }

    fun getAddLocationViewModel() = viewModel

    companion object {
        fun intent(context: Context) =
                Intent(context, AddLocationActivity::class.java)

        const val ADD_SUCCESS = 2
    }
}