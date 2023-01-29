package com.mmd.cityweather.common.services

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mmd.cityweather.common.domain.repositories.CurrentWeatherRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UpdateWeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    val currentWeatherRepository: CurrentWeatherRepository
) : Worker(appContext, workerParameters) {


    override fun doWork(): Result {
        //
        currentWeatherRepository.getCurrentWeather(123)
        return Result.success()
    }
}
