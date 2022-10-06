package com.mmd.cityweather.common.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentWeatherApi(
    @Json(name = "base")
    val base: String?,
    @Json(name = "clouds")
    val clouds: CloudsApi?,
    @Json(name = "cod")
    val cod: Int?,
    @Json(name = "coord")
    val coord: Coord?,
    @Json(name = "dt")
    val dt: Long?,
    @Json(name = "id")
    val id: Long?,
    @Json(name = "main")
    val main: Main?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "rain")
    val rain: RainApi?,
    @Json(name = "sys")
    val sys: Sys?,
    @Json(name = "timezone")
    val timezone: Int?,
    @Json(name = "visibility")
    val visibility: Int?,
    @Json(name = "weather")
    val weather: List<WeatherApi?>?,
    @Json(name = "wind")
    val wind: WindApi?
)

@JsonClass(generateAdapter = true)
data class CloudsApi(
    @Json(name = "all")
    val all: Int?
)

@JsonClass(generateAdapter = true)
data class Coord(
    @Json(name = "lat")
    val lat: Double?,
    @Json(name = "lon")
    val lon: Double?
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "feels_like")
    val feelsLike: Double?,
    @Json(name = "humidity")
    val humidity: Int?,
    @Json(name = "pressure")
    val pressure: Int?,
    @Json(name = "temp")
    val temp: Double?,
    @Json(name = "temp_max")
    val tempMax: Double?,
    @Json(name = "temp_min")
    val tempMin: Double?
)

@JsonClass(generateAdapter = true)
data class RainApi(
    @Json(name = "1h")
    val h: Double?
)

@JsonClass(generateAdapter = true)
data class Sys(
    @Json(name = "country")
    val country: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "sunrise")
    val sunrise: Int?,
    @Json(name = "sunset")
    val sunset: Int?,
    @Json(name = "type")
    val type: Int?
)

@JsonClass(generateAdapter = true)
data class WeatherApi(
    @Json(name = "description")
    val description: String?,
    @Json(name = "icon")
    val icon: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "main")
    val main: String?
)

@JsonClass(generateAdapter = true)
data class WindApi(
    @Json(name = "deg")
    val deg: Int?,
    @Json(name = "speed")
    val speed: Double?
)
