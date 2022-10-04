package com.mmd.cityweather.currentweather.domain

import com.mmd.cityweather.common.domain.repositories.CurrentWeatherRepository
import javax.inject.Inject

class GetCurrentWeather @Inject constructor(private val currentWeatherRepository: CurrentWeatherRepository) {

    operator fun invoke(cityId: Long) = currentWeatherRepository
        .getCurrentWeather(cityId)
}
