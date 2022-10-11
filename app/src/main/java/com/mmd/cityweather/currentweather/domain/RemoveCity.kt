package com.mmd.cityweather.currentweather.domain

import com.mmd.cityweather.common.domain.repositories.CityRepository
import javax.inject.Inject

class RemoveCity @Inject constructor(private val cityRepository: CityRepository) {

    suspend operator fun invoke(cityId: Long) {
        cityRepository.deleteCityById(cityId)
    }
}