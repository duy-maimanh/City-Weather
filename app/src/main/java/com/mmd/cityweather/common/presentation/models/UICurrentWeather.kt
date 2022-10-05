package com.mmd.cityweather.common.presentation.models

import com.mmd.cityweather.common.presentation.models.mappers.UIMapper

data class UICurrentWeather(
    val cityName: String,
    val temp: Double,
    val tempFeelLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val cloudiness: Int,
    val visibility: Int,
    val pressure: Int
)
