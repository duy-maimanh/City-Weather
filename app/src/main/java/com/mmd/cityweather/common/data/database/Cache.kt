package com.mmd.cityweather.common.data.database

import com.mmd.cityweather.common.data.database.models.cacheweather.CachedCurrentWeathers
import com.mmd.cityweather.common.data.database.models.cacheweather.CachedForecastWeathers
import com.mmd.cityweather.common.data.database.models.cities.Cities
import io.reactivex.Flowable

interface Cache {
    suspend fun storeCurrentWeather(currentWeather: CachedCurrentWeathers)
    fun getCurrentWeather(cityId: Long): Flowable<CachedCurrentWeathers>
    suspend fun storeCity(city: Cities)
    fun getCityInfoById(cityId: Long): Flowable<Cities>
    suspend fun cityIsExist(): Boolean
    suspend fun deleteCityById(cityId: Long)
    suspend fun storeForecastWeather(forecastWeathers: List<CachedForecastWeathers>)
    fun getForecastWeather(cityId: Long): Flowable<List<CachedForecastWeathers>>
    fun subscribeCityFromDatabase(): Flowable<List<Cities>>
    suspend fun deleteCityById(idList: List<Long>)
    suspend fun getAllCityIdInDatabase(): List<Long>
    suspend fun getALlCity(): List<Cities>
}
