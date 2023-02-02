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

package com.mmd.cityweather.forecastweatherdetail.domain

import com.mmd.cityweather.common.domain.model.ForecastWeatherDetail
import com.mmd.cityweather.common.domain.repositories.ForecastWeatherRepository
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastWeather @Inject constructor(
    private val forecastWeatherRepository: ForecastWeatherRepository
) {
    operator fun invoke(cityId: Long): Flowable<ForecastWeatherDetail> {
        return forecastWeatherRepository.getForecastWeather(cityId)
    }

    suspend fun forecastWeather(cityId: Long, lat: Double, lon: Double) {
        withContext(Dispatchers.IO) {
            val forecastWeathers =
                forecastWeatherRepository.requestForecastWeather(
                    cityId,
                    lat,
                    lon
                )
            forecastWeatherRepository.storeForecastWeather(forecastWeathers)
        }
    }
}
