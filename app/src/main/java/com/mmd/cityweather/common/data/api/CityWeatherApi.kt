package com.mmd.cityweather.common.data.api

import com.mmd.cityweather.common.data.api.model.WeatherApi
import retrofit2.http.GET
import retrofit2.http.Query

interface CityWeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query(ApiParameters.LAT) lat: Long,
        @Query(ApiParameters.LONG) lon: Long,
        @Query(ApiParameters.APIKEY) apiKey: Long,
    ): WeatherApi
}
