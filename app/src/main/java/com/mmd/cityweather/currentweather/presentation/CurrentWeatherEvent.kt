package com.mmd.cityweather.currentweather.presentation

sealed class CurrentWeatherEvent {
    object RequestInitCurrentWeather : CurrentWeatherEvent()
    object RequestRecentCurrentWeather : CurrentWeatherEvent()
}
