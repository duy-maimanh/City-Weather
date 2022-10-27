package com.mmd.cityweather.common.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastWeatherApi(
    @Json(name = "cnt")
    val cnt: Int?,
    @Json(name = "cod")
    val cod: String?,
    @Json(name = "list")
    val forecast: List<ForecastApi>?,
    @Json(name = "message")
    val message: Int?
)

@JsonClass(generateAdapter = true)
data class ForecastApi(
    @Json(name = "clouds")
    val clouds: CloudsApi?,
    @Json(name = "dt")
    val dt: Long?,
    @Json(name = "dt_txt")
    val dtTxt: String?,
    @Json(name = "main")
    val main: Main?,
    @Json(name = "pop")
    val pop: Double?,
    @Json(name = "rain")
    val rain: RainApi?,
    @Json(name = "sys")
    val sys: Sys?,
    @Json(name = "visibility")
    val visibility: Int?,
    @Json(name = "weather")
    val weather: List<WeatherApi?>?,
    @Json(name = "wind")
    val wind: WindApi?
)
