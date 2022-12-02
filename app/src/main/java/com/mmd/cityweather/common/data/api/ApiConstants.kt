package com.mmd.cityweather.common.data.api

object ApiConstants {
    const val BASE_ENDPOINT = "https://api.openweathermap.org/data/2.5/"
    const val API_KEY = "b770acc1fd179fe7756e31362dc31533"
    const val CURRENT_WEATHER_ENDPOINT = "weather"
    const val FORECAST_WEATHER_ENDPOINT = "forecast"
    const val DEFAULT_UNIT = "metric"
    const val BASE_IMAGE_URL = "http://openweathermap.org/img/wn/"
    const val IMAGE_SUFFIX = "@2x.png"
    const val LICENSE_URL = "https://openweathermap.org/"
}

object ApiParameters {
    const val LAT = "lat"
    const val LONG = "lon"
}
