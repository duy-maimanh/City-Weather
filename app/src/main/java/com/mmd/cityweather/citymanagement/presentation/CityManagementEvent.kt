package com.mmd.cityweather.citymanagement.presentation

sealed class CityManagementEvent {
    object DeleteCity : CityManagementEvent()
    object AddCity : CityManagementEvent()
    data class ChangeMode(val editMode: Boolean) : CityManagementEvent()
}
