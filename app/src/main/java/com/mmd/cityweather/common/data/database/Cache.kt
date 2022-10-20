package com.mmd.cityweather.common.data.database

import com.mmd.cityweather.common.data.database.models.cacheweather.CachedCurrentWeathers
import com.mmd.cityweather.common.data.database.models.cities.Cities
import io.reactivex.Flowable

interface Cache {
    suspend fun storeCurrentWeather(currentWeather: CachedCurrentWeathers)
    fun getCurrentWeather(cityId: Long): Flowable<CachedCurrentWeathers>
    suspend fun storeCity(city: Cities)
    fun getCityInfo(cityId: Long): Flowable<Cities>
    suspend fun cityIsExist(): Boolean
    suspend fun deleteCityById(cityId: Long)
}
