package com.mmd.cityweather.common.data

import com.mmd.cityweather.common.data.api.CityWeatherApi
import com.mmd.cityweather.common.data.api.model.mappers.ApiForecastMapper
import com.mmd.cityweather.common.data.api.model.mappers.ApiWeatherMapper
import com.mmd.cityweather.common.data.database.Cache
import com.mmd.cityweather.common.data.database.models.cacheweather.CachedForecastWeathers
import com.mmd.cityweather.common.domain.model.ForecastWeatherDetail
import com.mmd.cityweather.common.domain.repositories.ForecastWeatherRepository
import io.reactivex.Flowable
import javax.inject.Inject

class ForecastWeatherRepositoryImpl @Inject constructor(
    private val api: CityWeatherApi,
    private val apiForecastMapper: ApiForecastMapper,
    private val cache: Cache
) :
    ForecastWeatherRepository {
    override suspend fun requestForecastWeather(
        cityId: Long,
        lat: Double,
        lon: Double
    ): ForecastWeatherDetail {

        val forecastWeather = api.getForecastWeather(lat, lon)
        return apiForecastMapper.mapToDomain(forecastWeather).apply {
            this.cityId = cityId
        }
    }

    override suspend fun storeForecastWeather(forecastWeatherDetail: ForecastWeatherDetail) {
        cache.storeForecastWeather(forecastWeatherDetail.forecastDetails.map {
            CachedForecastWeathers.fromDomain(
                forecastWeatherDetail.cityId,
                it
            )
        })
    }

    override fun getForecastWeather(cityId: Long): Flowable<ForecastWeatherDetail> {
        return cache.getForecastWeather(cityId).distinctUntilChanged().map {
            val listForeCast = it.map { it.toDomain() }
            ForecastWeatherDetail(cityId, listForeCast)
        }
    }
}
