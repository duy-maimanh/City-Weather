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

package com.mmd.cityweather.common.data.api.model.mappers

import com.mmd.cityweather.common.data.api.model.CurrentWeatherApi
import com.mmd.cityweather.common.domain.model.CityCurrentWeatherDetail
import java.util.*
import javax.inject.Inject

class ApiWeatherMapper @Inject constructor() :
    ApiMapper<CurrentWeatherApi, CityCurrentWeatherDetail> {
    companion object {
        const val CITY_ID_UNKNOWN = -1L
    }

    override fun mapToDomain(apiEntity: CurrentWeatherApi): CityCurrentWeatherDetail {
        // the city id and time updated will be update by local.
        return CityCurrentWeatherDetail(
            apiEntity.id ?: throw Throwable("Weather must not empty"),
            CITY_ID_UNKNOWN,
            apiEntity.weather?.get(0)?.id.orEmpty(),
            apiEntity.weather?.get(0)?.main.orEmpty(),
            apiEntity.weather?.get(0)?.description.orEmpty(),
            apiEntity.weather?.get(0)?.icon.orEmpty(),
            apiEntity.main?.temp ?: 0.0,
            apiEntity.main?.feelsLike ?: 0.0,
            apiEntity.main?.tempMin ?: 0.0,
            apiEntity.main?.tempMax ?: 0.0,
            apiEntity.main?.pressure ?: 0,
            apiEntity.main?.humidity ?: 0,
            apiEntity.visibility ?: 0,
            apiEntity.wind?.speed ?: 0.0,
            apiEntity.wind?.deg ?: 0,
            apiEntity.clouds?.all ?: 0,
            Date((apiEntity.dt ?: 0) * 1000L)
        )
    }
}
