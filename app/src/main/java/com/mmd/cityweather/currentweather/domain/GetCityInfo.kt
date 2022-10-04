package com.mmd.cityweather.currentweather.domain

import com.mmd.cityweather.common.domain.repositories.CityRepository
import javax.inject.Inject

class GetCityInfo @Inject constructor(private val cityRepository: CityRepository) {

}