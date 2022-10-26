package com.mmd.cityweather.common.presentation.models

import com.mmd.cityweather.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class UIForecastWeather(
    val date: Date,
    val icon: String,
    val weatherDescription: String,
    val minTemp: Double,
    val maxTemp: Double,
    val temp: Double
) {
    fun getMinimizeDisplayDate(): Int? {
        val now: LocalDate = LocalDate.now()
        val tomorrow: LocalDate = LocalDate.now().plusDays(1)
        return if (now == date.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate()
        ) {
            R.string.today
        } else if (tomorrow == date.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate()
        ) {
            R.string.tomorrow
        } else {
            null
        }
    }

    fun getHoursOfForeCast(): String {
        val dateFormat = SimpleDateFormat("HH:mm")
        return dateFormat.format(date)
    }

    fun getFullDisplayDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy/ HH:mm")
        return dateFormat.format(date)
    }
}
