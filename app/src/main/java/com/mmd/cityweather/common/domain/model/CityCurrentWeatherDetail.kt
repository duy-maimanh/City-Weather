package com.mmd.cityweather.common.domain.model

import java.util.Date

data class CityCurrentWeatherDetail(
    val id: Long,
    var cityId: Long,
    val conditionId: String,
    val conditionName: String,
    val conditionDescription: String,
    val conditionIcon: String,
    val temp: Double,
    val tempFeelLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val cloudiness: Int,
    val timeOfData: Date
)
