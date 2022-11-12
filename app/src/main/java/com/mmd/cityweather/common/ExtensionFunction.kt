package com.mmd.cityweather.common

import android.util.Log
import com.mmd.cityweather.BuildConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


inline fun CoroutineScope.createExceptionHandler(
    message: String,
    crossinline action: (throwable: Throwable) -> Unit
) = CoroutineExceptionHandler { _, throwable ->
    if (BuildConfig.DEBUG) {
        Log.e(throwable.message, message)
        throwable.printStackTrace()
    }
    launch {
        action(throwable)
    }
}
