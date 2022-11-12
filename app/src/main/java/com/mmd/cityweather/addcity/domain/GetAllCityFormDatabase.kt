package com.mmd.cityweather.addcity.domain

import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.domain.repositories.CityRepository
import javax.inject.Inject

class GetAllCityFormDatabase @Inject constructor(private val repository: CityRepository) {

    suspend operator fun invoke(): List<CityInfoDetail> {
        return repository.getAllCityFromDatabase()
    }
}