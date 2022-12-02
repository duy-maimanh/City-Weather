package com.mmd.cityweather.privacy.domain

import com.mmd.cityweather.common.data.preferences.CityWeatherPreferences
import javax.inject.Inject

class UserApproveLocation @Inject constructor(
    private val weatherPreferences: CityWeatherPreferences
) {

    operator fun invoke() = weatherPreferences.isUserApproveLocation()

    fun set(status: Boolean) = weatherPreferences.setUserApproveLocation(status)
}
