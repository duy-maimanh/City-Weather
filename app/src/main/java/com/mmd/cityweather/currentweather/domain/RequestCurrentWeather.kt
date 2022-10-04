package com.mmd.cityweather.currentweather.domain

import com.mmd.cityweather.common.domain.repositories.CurrentWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestCurrentWeather @Inject constructor(
    private val currentWeatherRepository:
    CurrentWeatherRepository
) {

    suspend operator fun invoke(
        cityId: Long,
        latitude: Float,
        longitude: Float
    ) {
        withContext(Dispatchers.IO) {
            val currentWeather =
                currentWeatherRepository.requestNewCurrentWeather(
                    cityId,
                    latitude,
                    longitude
                )
            currentWeatherRepository.storeCurrentWeather(currentWeather)
        }
    }
}
