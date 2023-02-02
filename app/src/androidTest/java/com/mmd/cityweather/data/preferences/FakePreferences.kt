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
