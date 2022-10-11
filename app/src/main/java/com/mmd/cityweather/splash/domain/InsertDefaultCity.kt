package com.mmd.cityweather.splash.domain

import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.domain.repositories.CityRepository
import javax.inject.Inject

class InsertDefaultCity @Inject constructor(private val cityRepository: CityRepository) {

    suspend operator fun invoke(city: CityInfoDetail) {
        cityRepository.insertCity(city)
        // after insert default city, also set it is selected city.
        // Selected city mean : the city will be forecast when ever users
        // open the app.
        cityRepository.setSelectedCity(city.cityId)
    }
}
