package com.mmd.cityweather.addcity.presentation

sealed class AddCityBottomSheetEvent {
    object LoadTopCities : AddCityBottomSheetEvent()
    data class SearchRequest(val request: String) : AddCityBottomSheetEvent()
    data class AddCity(val position: Int) : AddCityBottomSheetEvent()
}
