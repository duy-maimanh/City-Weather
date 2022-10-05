package com.mmd.cityweather.common.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityWeatherPreferences @Inject constructor(
    @ApplicationContext context: Context
) : Preferences {
    companion object {
        private const val PREFERENCES_NAME = "city_weather"
        private const val DEFAULT_CITY_ID = "default_city_id"
    }

    private val preferences = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    override fun putSelectedCityId(id: Long) {
        edit { putLong(DEFAULT_CITY_ID, id) }
    }

    override fun getSelectedCityId(): Long {
        return preferences.getLong(DEFAULT_CITY_ID, -1)
    }

    private inline fun edit(block: SharedPreferences.Editor.() -> Unit) {
        with(preferences.edit()) {
            block()
            commit()
        }
    }
}
