package com.mmd.cityweather.common.domain.repositories

import com.mmd.cityweather.common.domain.model.ForecastWeatherDetail
import io.reactivex.Flowable

interface ForecastWeatherRepository {
    suspend fun requestForecastWeather(
        cityId: Long,
        lat: Double,
        lon: Double
    ): ForecastWeatherDetail

    suspend fun storeForecastWeather(forecastWeatherDetail: ForecastWeatherDetail)

    fun getForecastWeather(cityId: Long): Flowable<ForecastWeatherDetail>
}
