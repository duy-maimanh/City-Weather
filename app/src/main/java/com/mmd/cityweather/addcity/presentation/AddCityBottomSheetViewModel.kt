package com.mmd.cityweather.addcity.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmd.cityweather.addcity.domain.AddCity
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
) : ViewModel() {
    private val _state = MutableStateFlow(AddCityBottomSheetViewState())
    val state: StateFlow<AddCityBottomSheetViewState> = _state.asStateFlow()
    private var cityFromDisk = emptyList<CityInfoDetail>()

    fun onEvent(event: AddCityBottomSheetEvent) {
        when (event) {
            is AddCityBottomSheetEvent.AddCity -> {
                addCityAndFinish(event.position)
            }
            is AddCityBottomSheetEvent.LoadTopCities -> {
                getTopCity()
            }
            is AddCityBottomSheetEvent.SearchRequest -> {
                searchCity(event.request)
            }
        }
    }

    private fun addCityAndFinish(pos: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val citySelected = _state.value.topCity[pos]
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
        val results = cityFromDisk.filter {
            it.ascii?.lowercase()?.contains(searchQuery.lowercase()) ?: false
        }.map { UICity(it.cityId, it.name) }
        _state.update { oldState ->
            oldState.copy(searchCity = results)
        }
    }
}
