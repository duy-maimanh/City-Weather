package com.mmd.cityweather.currentweather.presentation

import com.mmd.cityweather.common.presentation.Event
import com.mmd.cityweather.common.presentation.models.UICurrentWeather

data class CurrentWeatherViewState(
    val loading: Boolean = true,
    val weather: UICurrentWeather? = null,
    val failure: Event<Throwable>? = null
)