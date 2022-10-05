package com.mmd.cityweather.currentweather.domain

import com.mmd.cityweather.common.domain.repositories.CityRepository
import javax.inject.Inject

class GetSelectedCityInfo @Inject constructor(private val cityRepository: CityRepository) {
    operator fun invoke() = cityRepository.getSelectedCityInfo()
}
