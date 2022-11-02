package com.mmd.cityweather.addcity.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmd.cityweather.addcity.domain.GetTopCityInfo
import com.mmd.cityweather.citymanagement.presentation.CityManagementViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCityBottomSheetViewModel @Inject constructor(
    private val getTopCityInfo: GetTopCityInfo,
) : ViewModel() {
    private val _state = MutableStateFlow(AddCityBottomSheetViewState())
    val state: StateFlow<AddCityBottomSheetViewState> = _state.asStateFlow()

    fun onEvent(event: AddCityBottomSheetEvent) {
        when (event) {
            is AddCityBottomSheetEvent.AddCity -> {
                event.position
            }
            is AddCityBottomSheetEvent.LoadTopCities -> {
                getTopCity()
            }
            is AddCityBottomSheetEvent.SearchRequest -> {
                event.request
            }
        }
    }

    private fun getTopCity() {
        viewModelScope.launch {
            val topCities = getTopCityInfo.getAllCity()
            _state.update { oldState ->
                oldState.copy(topCity = topCities)
            }
        }
    }
}
