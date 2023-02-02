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

import com.mmd.cityweather.common.data.api.model.ForecastWeatherApi
import com.mmd.cityweather.common.domain.model.ForecastDetail
import com.mmd.cityweather.common.domain.model.ForecastWeatherDetail
import java.util.*
import javax.inject.Inject

class ApiForecastMapper @Inject constructor() :
    ApiMapper<ForecastWeatherApi, ForecastWeatherDetail> {

    override fun mapToDomain(apiEntity: ForecastWeatherApi): ForecastWeatherDetail {
        val updateDate = Date(System.currentTimeMillis())
        return ForecastWeatherDetail(
            -1L,
            apiEntity.forecast?.map {
                ForecastDetail(
                    it.main?.temp ?: 0.0,
                    it.main?.tempMin ?: 0.0,
                    it.main?.tempMax ?: 0.0,
                    it.weather?.get(0)?.icon ?: "",
                    it.weather?.get(0)?.description ?: "",
                    Date((it.dt ?: 0) * 1000L),
                    updateDate
                )
            } ?: emptyList()
        )
    }
}
