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

package com.mmd.cityweather.currentweather.presentation

import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.presentation.Event
import com.mmd.cityweather.common.presentation.models.UICurrentWeather
import com.mmd.cityweather.common.presentation.models.UIForecastWeather
import com.mmd.cityweather.currentweather.domain.model.CityId

data class CurrentWeatherViewState(
    val loading: Boolean = true,
    val isFirstInit: Boolean = true,
    val hasCityInfo: Boolean = false,
    val weather: UICurrentWeather? = null,
    val forecastWeather: List<UIForecastWeather>? = null,
    val moveToCorrectLocation: Boolean = false,
    val failure: Event<Throwable>? = null,
    val openForecastDetail: List<CityId> = emptyList(),
    val startAutoUpdate: Event<Pair<Boolean, CityInfoDetail>>? = null,
    val isShowLocationExplainDialog: Event<Boolean>? = null,
    val isUserApproveForLocation: Event<Boolean>? = null
)
