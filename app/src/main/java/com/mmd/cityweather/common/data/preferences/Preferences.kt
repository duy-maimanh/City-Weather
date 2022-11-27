package com.mmd.cityweather.common.data.preferences

interface Preferences {

    fun putSelectedCityId(id: Long)

    fun getSelectedCityId(): Long

    fun getAutoUpdateWeatherStatus(): Boolean
}
