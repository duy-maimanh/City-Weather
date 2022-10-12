package com.mmd.cityweather.common.data

import com.mmd.cityweather.common.domain.model.ForecastWeatherDetail
import com.mmd.cityweather.common.domain.repositories.ForecastWeatherRepository

class ForecastWeatherRepositoryImpl: ForecastWeatherRepository {
    override suspend fun getForecastWeather(cityId: Long): ForecastWeatherDetail {
        TODO("Not yet implemented")
    }
}