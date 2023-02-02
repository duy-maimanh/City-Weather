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
