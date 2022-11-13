package com.mmd.cityweather.citymanagement.domain

import com.mmd.cityweather.common.domain.repositories.CityRepository
import javax.inject.Inject

class DeleteCityById @Inject constructor(private val cityRepository: CityRepository) {
    suspend operator fun invoke(idList: List<Long>) {
        cityRepository.deleteCityById(idList)
    }
}
