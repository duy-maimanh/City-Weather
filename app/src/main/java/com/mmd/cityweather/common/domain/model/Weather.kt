package com.mmd.cityweather.common.domain.model

data class Weather(
    val location: String,
    val degree: Int,
    val windSpeed: Int,
    val humidity: Int,
    val pressure: Float
)
