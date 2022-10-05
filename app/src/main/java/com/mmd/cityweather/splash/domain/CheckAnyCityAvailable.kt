package com.mmd.cityweather.splash.domain

import com.mmd.cityweather.common.domain.repositories.CityRepository
import javax.inject.Inject

class CheckAnyCityAvailable @Inject constructor(private val cityRepository: CityRepository) {

    suspend operator fun invoke(): Boolean = cityRepository.isExist()
}
