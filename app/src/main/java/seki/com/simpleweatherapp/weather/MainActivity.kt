package seki.com.simpleweatherapp.weather

import android.app.SearchManager
import android.app.Service
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.Toast
import seki.com.simpleweatherapp.R
import seki.com.simpleweatherapp.weather.ui.detail.WeatherDetailActivity
import seki.com.simpleweatherapp.weather.ui.list.WeatherListFragment

class MainActivity: AppCompatActivity() {

    private val mToolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(mToolbar)

        if (savedInstanceState == null) {
            val fragment = WeatherListFragment.getInstance()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment, WeatherListFragment.TAG).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_weather_list, menu)

        val searchManager = getSystemService(Service.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.isIconified = false

        searchView.setOnQueryTextListener(mSearchListener)

        searchView.onActionViewCollapsed() //hide SearchView editor and clear focus

        return true
    }

    private val mSearchListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let { search(it) }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }

    fun search(query: String) {
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show()
    }

    fun showDetailScreen(cityId: String) {
        val intent = WeatherDetailActivity.intent(this, cityId)
        startActivity(intent)
    }
}