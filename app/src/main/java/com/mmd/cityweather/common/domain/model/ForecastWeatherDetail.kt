package com.mmd.cityweather.common.domain.model

import java.util.Date

data class ForecastWeatherDetail(
    val id: Long,
    val cityId: Long,
    val forecastDetail: List<ForecastDetail>
)

data class ForecastDetail(
    val temp: Int, val minTemp: Int, val maxTemp: Int, val conditionId: Long,
    val conditionIcon: String, val conditionDescription: String, val time: Date
)
