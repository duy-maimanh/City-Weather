package com.mmd.cityweather.currentweather.presentation

import com.mmd.cityweather.common.presentation.Event
import com.mmd.cityweather.common.presentation.models.UICurrentWeather
import com.mmd.cityweather.common.presentation.models.UIForecastWeather

data class CurrentWeatherViewState(
    val loading: Boolean = true,
    val isFirstInit: Boolean = true,
    val hasCityInfo: Boolean = false,
    val weather: UICurrentWeather? = null,
    val forecastWeather: List<UIForecastWeather>? = null,
    val moveToCorrectLocation: Boolean = false,
    val failure: Event<Throwable>? = null
)
