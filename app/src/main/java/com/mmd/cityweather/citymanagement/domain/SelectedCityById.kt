package com.mmd.cityweather.citymanagement.domain

import com.mmd.cityweather.common.domain.repositories.CityRepository
import javax.inject.Inject

class SelectedCityById @Inject constructor(private val cityRepository: CityRepository) {

    operator fun invoke(cityId: Long) {
        cityRepository.setSelectedCity(cityId)
    }

    fun getSelectedCityId() = cityRepository.getSelectedCityId()
}
