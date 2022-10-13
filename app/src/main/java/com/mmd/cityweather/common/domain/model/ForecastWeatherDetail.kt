package com.mmd.cityweather.common.domain.model

import java.util.Date

data class ForecastWeatherDetail(
    var cityId: Long,
    val forecastDetails: List<ForecastDetail>
)

data class ForecastDetail(
    val temp: Double, val minTemp: Double, val maxTemp: Double, val conditionId: Long,
    val conditionIcon: String, val conditionDescription: String, val time: Date
)
