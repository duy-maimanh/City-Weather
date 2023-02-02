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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmd.cityweather.addcity.domain.AddCity
import com.mmd.cityweather.addcity.domain.GetAllCityFormDatabase
import com.mmd.cityweather.addcity.domain.GetAllCityFormDisk
import com.mmd.cityweather.addcity.domain.GetTopCityInfo
import com.mmd.cityweather.citymanagement.domain.model.UICity
import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.presentation.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCityBottomSheetViewModel @Inject constructor(
    private val getTopCityInfo: GetTopCityInfo,
    private val getAllCityFormDisk: GetAllCityFormDisk,
    private val addCity: AddCity,
    private val getAllCityFromDatabase: GetAllCityFormDatabase
) : ViewModel() {
    private val _state = MutableStateFlow(AddCityBottomSheetViewState())
    val state: StateFlow<AddCityBottomSheetViewState> = _state.asStateFlow()
    private var cityFromDisk = emptyList<CityInfoDetail>()

    fun onEvent(event: AddCityBottomSheetEvent) {
        when (event) {
            is AddCityBottomSheetEvent.AddCityByPosition -> {
                val citySelected = _state.value.topCity[event.position]
                addCityAndFinish(citySelected)
            }
            is AddCityBottomSheetEvent.LoadTopCities -> {
                getTopCity()
            }
            is AddCityBottomSheetEvent.SearchRequest -> {
                searchCity(event.request)
            }
            is AddCityBottomSheetEvent.AddCityById -> {
                val citySelected = _state.value.searchCity.find {
                    it.id == event.cityId
                }
                citySelected?.let { addCityAndFinish(it) }
            }
        }
    }

    private fun addCityAndFinish(citySelected: UICity) {
        viewModelScope.launch(Dispatchers.IO) {
            cityFromDisk.find { it.cityId == citySelected.id }?.let {
                addCity(it)
                _state.update { oldState ->
                    oldState.copy(addCityDone = Event(true))
                }
            }
        }
    }

    private fun getTopCity() {
        viewModelScope.launch(Dispatchers.IO) {
            cityFromDisk = getAllCityFormDisk()
            val topCities = getTopCityInfo.getAllCity(cityFromDisk)
            _state.update { oldState ->
                oldState.copy(topCity = topCities)
            }
        }
    }

    private fun searchCity(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { oldState ->
                oldState.copy(searchStatus = true)
            }
            val results = cityFromDisk.filter {
                it.ascii?.lowercase()?.contains(searchQuery.lowercase())
                    ?: false
            }
                .filterNot { searched -> searched.cityId in getAllCityFromDatabase().map { database -> database.cityId } }
                .map {
                    UICity(
                        it.cityId,
                        it.name
                    )
                }
            _state.update { oldState ->
                oldState.copy(searchCity = results, searchStatus = false)
            }
        }
    }
}
