package com.mmd.cityweather.currentweather.presentation

sealed class CurrentWeatherEvent {
    object RequestInitCurrentWeather : CurrentWeatherEvent()
    object RequestRecentCurrentWeather : CurrentWeatherEvent()
    data class ChangeNewLocation(val lat: Double, val log: Double) :
        CurrentWeatherEvent()
}
