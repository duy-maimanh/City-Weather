package com.mmd.cityweather.forecastweatherdetail.domain

import com.mmd.cityweather.common.domain.model.ForecastWeatherDetail
import com.mmd.cityweather.common.domain.repositories.ForecastWeatherRepository
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastWeather @Inject constructor(
    private val forecastWeatherRepository: ForecastWeatherRepository
) {
    operator fun invoke(cityId: Long): Flowable<ForecastWeatherDetail> {
        return forecastWeatherRepository.getForecastWeather(cityId)
    }

    suspend fun forecastWeather(cityId: Long, lat: Double, lon: Double) {
        withContext(Dispatchers.IO) {
            val forecastWeathers =
                forecastWeatherRepository.requestForecastWeather(
                    cityId,
                    lat,
                    lon
                )
            forecastWeatherRepository.storeForecastWeather(forecastWeathers)
        }
    }
}
