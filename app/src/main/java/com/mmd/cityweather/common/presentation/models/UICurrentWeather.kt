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

package com.mmd.cityweather.common.presentation.models

import java.text.SimpleDateFormat
import java.util.*

data class UICurrentWeather(
    val cityName: String,
    val weatherCondition: String,
    val temp: Double,
    val tempFeelLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val cloudiness: Int,
    val visibility: Int,
    val pressure: Int,
    val backgroundId: Int,
    private val timeOfData: Date
) {
    fun getTimeOfData(): String {
        val df = SimpleDateFormat("E d-M-yyyy hh:mm zzzz", Locale.US)
        return df.format(timeOfData)
    }
}
