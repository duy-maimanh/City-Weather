package com.mmd.cityweather.common.data

import com.mmd.cityweather.common.data.api.CityWeatherApi
import com.mmd.cityweather.common.data.api.model.mappers.ApiForecastMapper
import com.mmd.cityweather.common.data.api.model.mappers.ApiWeatherMapper
import com.mmd.cityweather.common.domain.model.ForecastWeatherDetail
import com.mmd.cityweather.common.domain.repositories.ForecastWeatherRepository
import javax.inject.Inject

class ForecastWeatherRepositoryImpl @Inject constructor(
    private val api: CityWeatherApi,
    private val apiForecastMapper: ApiForecastMapper,
) :
    ForecastWeatherRepository {
    override suspend fun requestForcastWeather(
        cityId: Long,
        lat: Double,
        lon: Double
    ): ForecastWeatherDetail {

        val forecastWeather = api.getForecastWeather(lat, lon)
        return apiForecastMapper.mapToDomain(forecastWeather).apply {
            this.cityId = cityId
        }
    }
}
