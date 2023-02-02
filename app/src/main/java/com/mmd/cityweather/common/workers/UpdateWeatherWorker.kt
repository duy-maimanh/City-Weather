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

package com.mmd.cityweather.common.workers

import android.annotation.SuppressLint
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.domain.repositories.CityRepository
import com.mmd.cityweather.common.domain.repositories.CurrentWeatherRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.runBlocking

@HiltWorker
class UpdateWeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    val currentWeatherRepository: CurrentWeatherRepository,
    val cityRepository: CityRepository
) : CoroutineWorker(appContext, workerParameters) {

    @SuppressLint("CheckResult")
    override suspend fun doWork(): Result {
        return try {
            runBlocking {
                val cityInfoDetail: CityInfoDetail =
                    cityRepository.getSelectedCityInfo().blockingFirst()
                val currentWeather =
                    currentWeatherRepository.requestNewCurrentWeather(
                        cityInfoDetail.cityId,
                        cityInfoDetail.lat,
                        cityInfoDetail.lon
                    )
                currentWeatherRepository.storeCurrentWeather(currentWeather)
                Result.success()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
