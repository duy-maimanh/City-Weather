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

package com.mmd.cityweather.common.domain.repositories

import com.mmd.cityweather.common.domain.model.CityInfoDetail
import io.reactivex.Flowable

interface CityRepository {
    fun getCityInformation(cityId: Long): Flowable<CityInfoDetail>
    suspend fun insertCity(city: CityInfoDetail)
    suspend fun isExist(): Boolean
    suspend fun getDefaultCity(): CityInfoDetail
    fun setSelectedCity(cityId: Long)
    fun getSelectedCityId(): Long
    fun getSelectedCityInfo(): Flowable<CityInfoDetail>
    suspend fun getAllCityInfoOnDisk(): List<CityInfoDetail>
    suspend fun deleteCityById(cityId: Long)
    fun subscribeCityInDatabase(): Flowable<List<CityInfoDetail>>
    suspend fun deleteCityById(idList: List<Long>)
    fun getTopCities(): List<Long>
    suspend fun getAllCityIdInDatabase(): List<Long>
    suspend fun getAllCityFromDatabase(): List<CityInfoDetail>
}
