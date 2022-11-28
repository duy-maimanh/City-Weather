package com.mmd.cityweather.common.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.mmd.cityweather.currentweather.domain.GetSelectedCity
import com.mmd.cityweather.currentweather.domain.RequestCurrentWeather
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class UpdateWeatherService : Service() {

    @Inject
    lateinit var requestCurrentWeather: RequestCurrentWeather

    @Inject
    lateinit var getSelectedCityInfo: GetSelectedCity


    // Binder given to clients
    private val binder = LocalBinder()

    companion object {
        const val TIME_TO_UPDATE = 30L // minute
    }

    private var skipFirstTime = false

    private val countHandler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            if (skipFirstTime) {
                doWork()
            } else {
                skipFirstTime = true
            }
            countHandler.postDelayed(this, TIME_TO_UPDATE * 60000)
        }
    }

    // call this function every TIME_TO_UPDATE minutes
    fun startUpdate() {
        countHandler.post(runnable)
    }

    fun stopUpdate() {
        countHandler.removeCallbacks(runnable)
    }

    // get current selected city and request new weather of it
    private fun doWork() {
        getSelectedCityInfo().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                runBlocking {
                    requestCurrentWeather(it.cityId, it.lat, it.lon)
                }
            }, {
               // no-op
            })
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): UpdateWeatherService = this@UpdateWeatherService
    }

    override fun onBind(p0: Intent?): IBinder {
        return binder
    }
}
