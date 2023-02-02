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
import com.mmd.cityweather.common.data.api.model.mappers.ApiWeatherMapper
import com.mmd.cityweather.common.data.database.Cache
import com.mmd.cityweather.common.data.database.models.cacheweather.CachedCurrentWeathers
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
        cityId: Long,
        lat: Double,
        lon: Double
    ): CityCurrentWeatherDetail {
        val apiWeather = api.getCurrentWeather(lat = lat, lon)
        return apiWeatherMapper.mapToDomain(apiWeather).apply {
            this.cityId = cityId
        }
    }

    override suspend fun storeCurrentWeather(
        weather: CityCurrentWeatherDetail
    ) {
        cache.storeCurrentWeather(CachedCurrentWeathers.fromDomain(weather))
    }
}
