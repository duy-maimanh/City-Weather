package com.mmd.cityweather.common.domain.repositories

import com.mmd.cityweather.common.domain.model.CityInfoDetail
import io.reactivex.Flowable

interface CityRepository {
    fun getCityInformation(cityId: Long): Flowable<CityInfoDetail>
    suspend fun insertCity(city: CityInfoDetail)
    suspend fun isExist(): Boolean
    suspend fun getDefaultCity(): CityInfoDetail
    fun setSelectedCity(cityId: Long)
    fun getSelectedCityInfo(): Flowable<CityInfoDetail>
    suspend fun getAllCityInfoOnDisk(): List<CityInfoDetail>
    suspend fun deleteCityById(cityId: Long)
    fun subscribeCityInDatabase(): Flowable<List<CityInfoDetail>>
    suspend fun deleteCityById(idList: List<Long>)
    fun getTopCities(): List<Long>
    suspend fun getAllCityIdInDatabase(): List<Long>
    suspend fun getAllCityFromDatabase(): List<CityInfoDetail>
}
