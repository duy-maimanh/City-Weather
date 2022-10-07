package com.mmd.cityweather.common.data

import com.mmd.cityweather.common.data.api.CityWeatherApi
import com.mmd.cityweather.common.data.api.model.mappers.ApiWeatherMapper
import com.mmd.cityweather.common.data.database.Cache
import com.mmd.cityweather.common.data.database.models.cacheweather.CachedWeathers
import com.mmd.cityweather.common.domain.model.CityCurrentWeatherDetail
import com.mmd.cityweather.common.domain.repositories.CurrentWeatherRepository
import io.reactivex.Flowable
import javax.inject.Inject

class CurrentWeatherRepositoryImpl @Inject constructor(
    private val api: CityWeatherApi,
    private val apiWeatherMapper: ApiWeatherMapper,
    private val cache: Cache
) : CurrentWeatherRepository {

    override fun getCurrentWeather(cityId: Long): Flowable<CityCurrentWeatherDetail> {
        return cache.getCurrentWeather(cityId).distinctUntilChanged().map {
            it.toDomain()
        }
    }

    override suspend fun requestNewCurrentWeather(
        cityId: Long, lat: Double, lon: Double
    ): CityCurrentWeatherDetail {
        val apiWeather = api.getCurrentWeather(lat = lat, lon)
        return apiWeatherMapper.mapToDomain(apiWeather).apply {
            this.cityId = cityId
        }
    }

    override suspend fun storeCurrentWeather(
        weather: CityCurrentWeatherDetail
    ) {
        cache.storeCurrentWeather(CachedWeathers.fromDomain(weather))
    }
}
