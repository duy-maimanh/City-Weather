package com.mmd.cityweather.common.presentation.models

import java.text.SimpleDateFormat
import java.util.*

data class UICurrentWeather(
    val cityName: String,
    val weatherCondition: String,
    val temp: Double,
    val tempFeelLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val cloudiness: Int,
    val visibility: Int,
    val pressure: Int,
    val backgroundId: Int,
    private val timeOfData: Date
) {
    fun getTimeOfData(): String {
        val df = SimpleDateFormat("E d-M-yyyy hh:mm zzzz", Locale.US)
        return df.format(timeOfData)
    }
}
