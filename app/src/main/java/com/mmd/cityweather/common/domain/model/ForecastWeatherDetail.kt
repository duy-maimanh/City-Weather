package com.mmd.cityweather.common.domain.model

import java.util.Date

data class ForecastWeatherDetail(
    var cityId: Long,
    val forecastDetails: List<ForecastDetail>
)

data class ForecastDetail(
    val temp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val conditionIcon: String,
    val conditionDescription: String,
    val timeOfForeCast: Date,
    val timeOfUpdate: Date
)
