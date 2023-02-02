/*
 * Developed by 2022 Duy Mai.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mmd.cityweather.common.data

import com.mmd.cityweather.common.data.api.CityWeatherApi
import com.mmd.cityweather.common.data.api.model.mappers.ApiForecastMapper
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
        cache.storeForecastWeather(
            forecastWeatherDetail.forecastDetails.map {
                CachedForecastWeathers.fromDomain(
                    forecastWeatherDetail.cityId,
                    it
                )
            }
        )
    }

    override fun getForecastWeather(cityId: Long): Flowable<ForecastWeatherDetail> {
        return cache.getForecastWeather(cityId).distinctUntilChanged().map {
            val listForeCast = it.map { it.toDomain() }
            ForecastWeatherDetail(cityId, listForeCast)
        }
    }
}
