package com.mmd.cityweather.common.data.api

import com.mmd.cityweather.common.data.api.model.CurrentWeatherApi
import retrofit2.http.GET
import retrofit2.http.Query

interface CityWeatherApi {

    @GET(ApiConstants.CURRENT_WEATHER_ENDPOINT)
    suspend fun getCurrentWeather(
        @Query(ApiParameters.LAT) lat: Float,
        @Query(ApiParameters.LONG) lon: Float,
    ): CurrentWeatherApi
}
