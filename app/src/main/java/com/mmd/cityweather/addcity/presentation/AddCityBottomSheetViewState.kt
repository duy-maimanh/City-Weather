package com.mmd.cityweather.addcity.presentation

import com.mmd.cityweather.citymanagement.domain.model.UICity
import com.mmd.cityweather.common.presentation.Event

data class AddCityBottomSheetViewState(
    var topCity: List<UICity> = emptyList(),
    var addCityDone: Event<Boolean>? = null
)
