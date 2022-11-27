package com.mmd.cityweather.currentweather.domain

import com.mmd.cityweather.common.data.preferences.CityWeatherPreferences
import javax.inject.Inject

class CheckAutoUpdateWeather @Inject constructor(private val cityWeatherPreferences: CityWeatherPreferences) {

    operator fun invoke(): Boolean = cityWeatherPreferences.getAutoUpdateWeatherStatus()
}
