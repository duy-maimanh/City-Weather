package com.mmd.cityweather.common.services

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.domain.repositories.CityRepository
import com.mmd.cityweather.common.domain.repositories.CurrentWeatherRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
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
                currentWeatherRepository.requestNewCurrentWeather(
                    cityInfoDetail.cityId,
                    cityInfoDetail.lat,
                    cityInfoDetail.lon
                )
                Log.d("Worker update", "run success")
                Result.success()
            }
        } catch (e: Exception) {
            Log.d("Worker update", "run failure")
            Result.failure()
        }
    }
}
