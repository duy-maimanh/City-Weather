package com.mmd.cityweather.common.data.api.interceptors

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {
    companion object {
        private const val TAG = "CityWeather Api"
    }

    override fun log(message: String) {
        Log.d(TAG, message)
    }
}
