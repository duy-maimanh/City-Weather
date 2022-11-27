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
    }

    private val cityPreference = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    private val settingPreference = PreferenceManager.getDefaultSharedPreferences(context)

    override fun putSelectedCityId(id: Long) {
        edit { putLong(DEFAULT_CITY_ID, id) }
    }

    override fun getSelectedCityId(): Long {
        return cityPreference.getLong(DEFAULT_CITY_ID, -1)
    }

    override fun getAutoUpdateWeatherStatus(): Boolean {
        return settingPreference.getBoolean(AUTO_UPDATE_WEATHER, false)
    }

    private inline fun edit(block: SharedPreferences.Editor.() -> Unit) {
        with(cityPreference.edit()) {
            block()
            commit()
        }
    }
}
