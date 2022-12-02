package com.mmd.cityweather.common.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityWeatherPreferences @Inject constructor(
    @ApplicationContext context: Context
) : Preferences {
    companion object {
        const val PREFERENCES_NAME = "city_weather"
        private const val DEFAULT_CITY_ID = "default_city_id"
        private const val AUTO_UPDATE_WEATHER = "auto_update_weather"
        private const val IS_SHOW_LOCATION_EXPLAIN = "is_show_location_explain"
        private const val IS_USER_APPROVE_LOCATION = "is_user_approve_location"
    }

    private val cityPreference = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    private val settingPreference =
        PreferenceManager.getDefaultSharedPreferences(context)

    override fun putSelectedCityId(id: Long) {
        editCity { putLong(DEFAULT_CITY_ID, id) }
    }

    override fun getSelectedCityId(): Long {
        return cityPreference.getLong(DEFAULT_CITY_ID, -1)
    }

    override fun getAutoUpdateWeatherStatus(): Boolean {
        return settingPreference.getBoolean(AUTO_UPDATE_WEATHER, false)
    }

    override fun isShowPrivacyExplain(): Boolean {
        return settingPreference.getBoolean(IS_SHOW_LOCATION_EXPLAIN, false)
    }

    override fun isUserApproveLocation(): Boolean {
        return settingPreference.getBoolean(IS_USER_APPROVE_LOCATION, false)
    }

    override fun setUserApproveLocation(status: Boolean) {
        editSetting { putBoolean(IS_USER_APPROVE_LOCATION, status) }
    }

    override fun setShowPrivacyExplain(status: Boolean) {
        editSetting { putBoolean(IS_SHOW_LOCATION_EXPLAIN, status) }
    }

    private inline fun editCity(block: SharedPreferences.Editor.() -> Unit) {
        with(cityPreference.edit()) {
            block()
            commit()
        }
    }

    private inline fun editSetting(block: SharedPreferences.Editor.() -> Unit) {
        with(settingPreference.edit()) {
            block()
            commit()
        }
    }
}
