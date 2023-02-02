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

package com.mmd.cityweather.citymanagement.presentation

sealed class CityManagementEvent {
    object DeleteCity : CityManagementEvent()
    object AddCity : CityManagementEvent()
    data class ChangeMode(val editMode: Boolean) : CityManagementEvent()
    data class UpdateDeleteCityList(val pos: Int, val isDelete: Boolean) :
        CityManagementEvent()
    data class SelectCity(val pos: Int) : CityManagementEvent()
}
