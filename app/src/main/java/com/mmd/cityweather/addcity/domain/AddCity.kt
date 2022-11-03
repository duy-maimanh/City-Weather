package com.mmd.cityweather.addcity.domain

import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.domain.repositories.CityRepository
import javax.inject.Inject

class AddCity @Inject constructor(private val cityRepository: CityRepository) {

    suspend operator fun invoke(city: CityInfoDetail) {
        cityRepository.insertCity(city)
    }
}
