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
    private val timeOfData: Date
) {
    fun getTimeOfData(): String {
        val df = SimpleDateFormat("E dd-M-yyyy hh:mm:ss zzzz", Locale.ENGLISH)
        return df.format(timeOfData)
    }
}
