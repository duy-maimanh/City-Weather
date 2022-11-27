package com.mmd.cityweather.currentweather.presentation

import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.presentation.Event
import com.mmd.cityweather.common.presentation.models.UICurrentWeather
import com.mmd.cityweather.common.presentation.models.UIForecastWeather
import com.mmd.cityweather.currentweather.domain.model.CityId

data class CurrentWeatherViewState(
    val loading: Boolean = true,
    val isFirstInit: Boolean = true,
    val hasCityInfo: Boolean = false,
    val weather: UICurrentWeather? = null,
    val forecastWeather: List<UIForecastWeather>? = null,
    val moveToCorrectLocation: Boolean = false,
    val failure: Event<Throwable>? = null,
    val openForecastDetail: List<CityId> = emptyList(),
    val startAutoUpdate: Pair<Boolean, CityInfoDetail>? = null
)
