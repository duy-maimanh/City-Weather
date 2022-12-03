package com.mmd.cityweather.data.preferences

import com.mmd.cityweather.common.data.preferences.Preferences

class FakePreferences : Preferences {
    companion object {
        private const val DEFAULT_CITY_ID = "default_city_id"
    }

    private val preferences = mutableMapOf<String, Any>()

    override fun putSelectedCityId(id: Long) {
        preferences[DEFAULT_CITY_ID] = id
    }

    override fun getSelectedCityId(): Long {
        return preferences[DEFAULT_CITY_ID] as Long
    }

    override fun getAutoUpdateWeatherStatus(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isShowPrivacyExplain(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isUserApproveLocation(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setShowPrivacyExplain(status: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setUserApproveLocation(status: Boolean) {
        TODO("Not yet implemented")
    }
}
