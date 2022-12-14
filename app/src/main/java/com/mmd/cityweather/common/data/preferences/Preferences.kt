package com.mmd.cityweather.common.data.preferences

interface Preferences {

    fun putSelectedCityId(id: Long)

    fun getSelectedCityId(): Long

    fun getAutoUpdateWeatherStatus(): Boolean

    fun isShowPrivacyExplain(): Boolean

    fun isUserApproveLocation(): Boolean

    fun setShowPrivacyExplain(status : Boolean)

    fun setUserApproveLocation(status: Boolean)
}
