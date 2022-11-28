package com.mmd.cityweather.citymanagement.presentation

import com.mmd.cityweather.citymanagement.domain.model.UICity

data class CityManagementViewState(
    val isEdit: Boolean = false,
    val cities: List<UICity> = emptyList(),
    val moveToCurrentWeather: Boolean = false
)
