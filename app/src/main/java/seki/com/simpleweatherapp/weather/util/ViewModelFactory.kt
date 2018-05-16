package seki.com.simpleweatherapp.weather.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
        private val creators: Map<Class<out ViewModel>,
                @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) throw IllegalStateException()

        try {
            return creator.get() as T
        } catch (e: Exception) {
            Log.d("error", e.message)
            throw RuntimeException()
        }
    }
}