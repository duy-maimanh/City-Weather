package com.mmd.cityweather.currentweather.domain

import com.mmd.cityweather.common.data.preferences.CityWeatherPreferences
import javax.inject.Inject

class ExplainDialogStatus @Inject constructor(
    private val weatherPreferences: CityWeatherPreferences
) {

    operator fun invoke() = weatherPreferences.isShowPrivacyExplain()

    fun set(status: Boolean) = weatherPreferences.setShowPrivacyExplain(status)
}
