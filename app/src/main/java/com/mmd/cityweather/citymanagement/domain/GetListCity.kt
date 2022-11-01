package com.mmd.cityweather.citymanagement.domain

import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.domain.repositories.CityRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetListCity @Inject constructor(private val cityRepository: CityRepository) {

    operator fun invoke(): Flowable<List<CityInfoDetail>> {
        return cityRepository.getAllCityInDatabase()
    }
}
