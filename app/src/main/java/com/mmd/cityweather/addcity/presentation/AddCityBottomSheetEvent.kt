package com.mmd.cityweather.addcity.presentation

sealed class AddCityBottomSheetEvent {
    object LoadTopCities : AddCityBottomSheetEvent()
    data class SearchRequest(val request: String) : AddCityBottomSheetEvent()
    data class AddCityByPosition(val position: Int) : AddCityBottomSheetEvent()
    data class AddCityById(val cityId: Long) : AddCityBottomSheetEvent()
}
