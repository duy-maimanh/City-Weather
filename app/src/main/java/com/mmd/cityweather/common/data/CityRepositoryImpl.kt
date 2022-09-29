package com.mmd.cityweather.common.data

import com.mmd.cityweather.common.data.database.Cache
import com.mmd.cityweather.common.data.database.models.cities.Cities
import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.domain.repositories.CityRepository
import io.reactivex.Flowable
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val cache: Cache
) : CityRepository {

    override fun getCityInformation(cityId: Long): Flowable<CityInfoDetail> {
        return cache.getCityInfo(cityId).map { it.toDomain() }
    }

    override suspend fun insertCity(city: CityInfoDetail) {
        cache.storeCity(Cities.fromDomain(city))
    }

}
