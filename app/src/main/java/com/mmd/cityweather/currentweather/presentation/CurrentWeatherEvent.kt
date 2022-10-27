package com.mmd.cityweather.currentweather.presentation

sealed class CurrentWeatherEvent {
    object RequestInitCurrentWeather : CurrentWeatherEvent()
    object RequestRecentCurrentWeather : CurrentWeatherEvent()
    object OpenForecastDetail : CurrentWeatherEvent()
    data class ChangeNewLocation(val lat: Double, val log: Double) :
        CurrentWeatherEvent()

}
