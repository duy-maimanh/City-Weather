/*
 * Developed by 2022 Duy Mai.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
