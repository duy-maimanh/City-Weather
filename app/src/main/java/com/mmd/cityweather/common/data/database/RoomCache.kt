package com.mmd.cityweather.common.data.database

import com.mmd.cityweather.common.data.database.daos.CitiesDao
import com.mmd.cityweather.common.data.database.daos.WeatherDao
import com.mmd.cityweather.common.data.database.models.cacheweather.CachedWeathers
import com.mmd.cityweather.common.data.database.models.cities.Cities
import io.reactivex.Flowable
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val weatherDao: WeatherDao,
    private val cityDao: CitiesDao
) : Cache {

    override suspend fun storeCurrentWeather(currentWeather: CachedWeathers) {
        weatherDao.insertCurrentWeather(currentWeather)
    }

    override fun getCurrentWeather(cityId: Long): Flowable<CachedWeathers> {
        return weatherDao.getLatestWeather(cityId)
    }

    override suspend fun storeCity(city: Cities) {
        cityDao.insertCity(city)
    }

    override fun getCityInfo(cityId: Long): Flowable<Cities> {
        return cityDao.getInfoCity()
    }

    override suspend fun cityIsExist(): Boolean {
        return cityDao.isExists()
    }

    override suspend fun deleteCityById(cityId: Long) {
        return cityDao.delete(cityId)
    }
}
