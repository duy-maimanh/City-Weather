package com.mmd.cityweather.forecastweatherdetail.presentation

import com.mmd.cityweather.common.presentation.Event
import com.mmd.cityweather.common.presentation.models.UIForecastWeather

data class ForecastWeatherViewState(
    val forecastWeathers: List<UIForecastWeather>? = null,
    val onFailureMessage: Event<Throwable>? = null
)
