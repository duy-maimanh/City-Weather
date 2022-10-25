package com.mmd.cityweather.currentweather.presentation

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.presentation.Event
import com.mmd.cityweather.common.presentation.models.UICurrentWeather
import com.mmd.cityweather.common.presentation.models.UIForecastWeather
import com.mmd.cityweather.currentweather.domain.*
import com.mmd.cityweather.splash.domain.InsertDefaultCity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeather: GetCurrentWeather,
    private val requestCurrentWeather: RequestCurrentWeather,
    private val getSelectedCityInfo: GetSelectedCity,
    private val cityInfoByLocation: GetCityByLocation,
    private val removeCity: RemoveCity,
    private val insertDefaultCity: InsertDefaultCity,
    private val compositeDisposable: CompositeDisposable,
    private val getBackgroundForCurrentWeather: GetBackgroundForCurrentWeather,
    private val forecastWeather: ForecastWeather
) : ViewModel() {
    private val _state = MutableStateFlow(CurrentWeatherViewState())
    val state: StateFlow<CurrentWeatherViewState> = _state.asStateFlow()
    private lateinit var selectedCityInfo: CityInfoDetail

    init {
        subscribeToSelectedCity()
    }

    fun onEven(event: CurrentWeatherEvent) {
        when (event) {
            is CurrentWeatherEvent.RequestInitCurrentWeather -> {
                requestCurrentWeather()
            }
            is CurrentWeatherEvent.RequestRecentCurrentWeather -> {
                requestCurrentWeather()
            }
            is CurrentWeatherEvent.ChangeNewLocation -> {
                getCityInfoByLocation(event.lat, event.log)
            }
        }
    }

    private fun getCityInfoByLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            cityInfoByLocation(lat, lon)?.let { info ->
                if (info.cityId != selectedCityInfo.cityId) {
                    // if it is auto, remove it.
                    if (selectedCityInfo.isAuto) {
                        // remove the city from database
                        removeCity(selectedCityInfo.cityId)

                        // add new city if it already just replace it
                        // save selected into preference for next time open
                        insertDefaultCity(info)

                        // send event to open new fragment
                        _state.update { oldState ->
                            oldState.copy(moveToCorrectLocation = true)
                        }
                    }
                }
            }
        }
    }

    private fun requestCurrentWeather() {
        onLoadingStatus(true)
        viewModelScope.launch {
            requestCurrentWeather(
                selectedCityInfo.cityId,
                selectedCityInfo.lat,
                selectedCityInfo.lon
            )
            forecastWeather.forecastWeather(
                selectedCityInfo.cityId,
                selectedCityInfo.lat,
                selectedCityInfo.lon
            )
        }
    }

    private fun subscribeToSelectedCity() {
        getSelectedCityInfo().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                selectedCityInfo = it
                subscribeToCurrentWeatherUpdates()
                subscribeToForecastWeather()
                onHasCityInfo(true)
            }, {
                onFailure(it)
            }).addTo(compositeDisposable)
    }

    // get current weather from cache db.
    private fun subscribeToCurrentWeatherUpdates() {
        getCurrentWeather(selectedCityInfo.cityId).map { weatherInfo ->
            UICurrentWeather(
                selectedCityInfo.name,
                weatherInfo.conditionDescription.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                },
                weatherInfo.temp,
                weatherInfo.tempFeelLike,
                weatherInfo.humidity,
                weatherInfo.windSpeed,
                weatherInfo.cloudiness,
                weatherInfo.visibility,
                weatherInfo.pressure,
                getBackgroundForCurrentWeather(weatherInfo.conditionId.toInt()),
                weatherInfo.timeOfData
            )
        }.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                onNewWeather(it)
            }, {
                onFailure(it)
            }).addTo(compositeDisposable)
    }

    private fun subscribeToForecastWeather() {
        forecastWeather.invoke(selectedCityInfo.cityId)
            .map { forecastWeather ->
                forecastWeather.forecastDetails.map {
                    UIForecastWeather(
                        it.timeOfForeCast.toString(),
                        it.conditionIcon,
                        it.conditionDescription,
                        it.minTemp,
                        it.maxTemp,
                        it.temp
                    )
                }
            }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            }).addTo(compositeDisposable)
    }

    private fun onLoadingStatus(
        loadingStatus: Boolean, isFirstInit: Boolean = false
    ) {
        _state.update { oldState ->
            oldState.copy(loading = loadingStatus, isFirstInit = isFirstInit)
        }
    }

    private fun onHasCityInfo(status: Boolean) {
        _state.update { oldState ->
            oldState.copy(loading = false, hasCityInfo = status)
        }
    }

    private fun onNewWeather(weather: UICurrentWeather) {
        _state.update { oldState ->
            oldState.copy(loading = false, weather = weather)
        }
    }

    private fun onFailure(throwable: Throwable) {
        _state.update { oldState ->
            oldState.copy(loading = false, failure = Event(throwable))
        }
    }

    private inline fun benchmark(block: () -> Unit) {
        var timeRun = SystemClock.uptimeMillis()
        block()
        timeRun = SystemClock.uptimeMillis() - timeRun
        Log.d("Speed:", "$timeRun millis")
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
