package com.mmd.cityweather.common.data.api

import com.mmd.cityweather.common.data.api.model.CurrentWeatherApi
import com.mmd.cityweather.common.data.api.model.ForecastWeatherApi
import retrofit2.http.GET
import retrofit2.http.Query

interface CityWeatherApi {

    @GET(ApiConstants.CURRENT_WEATHER_ENDPOINT)
    suspend fun getCurrentWeather(
        @Query(ApiParameters.LAT) lat: Double,
        @Query(ApiParameters.LONG) lon: Double,
    ): CurrentWeatherApi

    @GET(ApiConstants.FORECAST_WEATHER_ENDPOINT)
    suspend fun getForecastWeather(
        @Query(ApiParameters.LAT) lat: Double,
        @Query(ApiParameters.LONG) lon: Double,
    ): ForecastWeatherApi
}
