package com.mmd.cityweather.common.data.database

import com.mmd.cityweather.common.data.database.models.cacheweather.CachedWeathers
import com.mmd.cityweather.common.data.database.models.cities.Cities
import io.reactivex.Flowable

interface Cache {
    suspend fun storeCurrentWeather(currentWeather: CachedWeathers)
    fun getCurrentWeather(cityId: Long): Flowable<CachedWeathers>
    suspend fun storeCity(city: Cities)
    fun getCityInfo(cityId: Long): Flowable<Cities>
}
