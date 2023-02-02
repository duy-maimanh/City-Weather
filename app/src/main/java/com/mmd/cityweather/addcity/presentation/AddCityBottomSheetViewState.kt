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

package com.mmd.cityweather.addcity.presentation

import com.mmd.cityweather.citymanagement.domain.model.UICity
import com.mmd.cityweather.common.presentation.Event

data class AddCityBottomSheetViewState(
    var topCity: List<UICity> = emptyList(),
    var addCityDone: Event<Boolean>? = null,
    var searchCity: List<UICity> = emptyList(),
    var searchStatus: Boolean = false
)
