package com.mmd.cityweather.addcity.domain

import com.mmd.cityweather.citymanagement.domain.model.UICity
import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.domain.repositories.CityRepository
import javax.inject.Inject

class GetTopCityInfo @Inject constructor(private val cityRepository: CityRepository) {

    private fun getTopCityId() = cityRepository.getTopCities()
    suspend fun getAllCity(cityFromDisk: List<CityInfoDetail>): List<UICity> {

        val cityAlreadyAdded = cityRepository.getAllCityIdInDatabase()

        val recommendedCity =
            getTopCityId().filterNot { it in cityAlreadyAdded }

        return cityFromDisk.filter {
            it.cityId in recommendedCity
        }.map {
            UICity(
                it.cityId, it.name
            )
        }
    }
}
