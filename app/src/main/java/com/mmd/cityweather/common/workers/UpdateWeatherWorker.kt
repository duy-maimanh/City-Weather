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
