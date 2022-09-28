package com.mmd.cityweather.common.domain.repositories

import com.mmd.cityweather.common.domain.model.CityInfoDetail
import io.reactivex.Flowable

interface CityRepository {
    fun getCityInformation(cityId: Long): Flowable<CityInfoDetail>
    suspend fun insertCity(city: CityInfoDetail)
}
