package com.mmd.cityweather.common.domain.repositories

import com.mmd.cityweather.common.domain.model.CityCurrentWeatherDetail
import io.reactivex.Flowable

interface CurrentWeatherRepository {
    fun getCurrentWeather(cityId: Long): Flowable<CityCurrentWeatherDetail>
    suspend fun requestNewCurrentWeather(cityId: Long, lat: Double, lon: Double):
            CityCurrentWeatherDetail
    suspend fun storeCurrentWeather(weather: CityCurrentWeatherDetail)
}
