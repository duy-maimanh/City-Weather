package com.mmd.cityweather.currentweather.presentation

import com.mmd.cityweather.common.domain.model.CityCurrentWeatherDetail
import com.mmd.cityweather.common.presentation.Event

data class CurrentWeatherViewState(
    val loading: Boolean = true,
    val weather: CityCurrentWeatherDetail? = null,
    val failure: Event<Throwable>? = null
)
