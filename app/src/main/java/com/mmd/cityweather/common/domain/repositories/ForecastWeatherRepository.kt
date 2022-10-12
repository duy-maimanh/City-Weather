package com.mmd.cityweather.common.domain.repositories

import com.mmd.cityweather.common.domain.model.ForecastWeatherDetail

interface ForecastWeatherRepository {
    suspend fun getForecastWeather(cityId: Long): ForecastWeatherDetail
}
