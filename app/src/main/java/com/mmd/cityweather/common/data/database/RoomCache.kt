package com.mmd.cityweather.common.data.database

import com.mmd.cityweather.common.data.database.daos.CitiesDao
import com.mmd.cityweather.common.data.database.daos.WeatherDao
import com.mmd.cityweather.common.data.database.models.cacheweather.CachedCurrentWeathers
import com.mmd.cityweather.common.data.database.models.cacheweather.CachedForecastWeathers
import com.mmd.cityweather.common.data.database.models.cities.Cities
import io.reactivex.Flowable
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val weatherDao: WeatherDao,
    private val cityDao: CitiesDao
) : Cache {

    override suspend fun storeCurrentWeather(currentWeather: CachedCurrentWeathers) {
        weatherDao.insertCurrentWeather(currentWeather)
    }

    override fun getCurrentWeather(cityId: Long): Flowable<CachedCurrentWeathers> {
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

    override suspend fun storeForecastWeather(forecastWeathers: List<CachedForecastWeathers>) {
        weatherDao.insertForecastWeather(forecastWeathers)
    }

    override fun getForecastWeather(cityId: Long): Flowable<List<CachedForecastWeathers>> {
        return weatherDao.getForecastWeather(cityId)
    }

    override fun getAllCity(): Flowable<List<Cities>> {
        return cityDao.getAllCity()
    }
}
