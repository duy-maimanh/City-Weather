package com.mmd.cityweather.currentweather.presentation

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.presentation.Event
import com.mmd.cityweather.common.presentation.models.UICurrentWeather
import com.mmd.cityweather.currentweather.domain.GetCityInfoByLocation
import com.mmd.cityweather.currentweather.domain.GetCurrentWeather
import com.mmd.cityweather.currentweather.domain.GetSelectedCityInfo
import com.mmd.cityweather.currentweather.domain.RequestCurrentWeather
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
    private val getSelectedCityInfo: GetSelectedCityInfo,
    private val compositeDisposable: CompositeDisposable,
    private val cityInfoByLocation: GetCityInfoByLocation
) : ViewModel() {
    private val _state = MutableStateFlow(CurrentWeatherViewState())
    val state: StateFlow<CurrentWeatherViewState> = _state.asStateFlow()
    private lateinit var defaultCityInfo: CityInfoDetail

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
                if (info.cityId != defaultCityInfo.cityId) {
                    // show dialog ask user want to change your location
                    // three options: no, yes, yes for every time.
                    _state.update { oldState ->
                        oldState.copy(differLocation = true)
                    }
                }
            }
        }
    }

    private fun requestCurrentWeather() {
        onLoadingStatus(true)
        viewModelScope.launch {
            requestCurrentWeather(
                defaultCityInfo.cityId, defaultCityInfo.lat, defaultCityInfo.lon
            )
        }
    }

    private fun subscribeToSelectedCity() {
        getSelectedCityInfo().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                defaultCityInfo = it
                subscribeToCurrentWeatherUpdates()
                onHasCityInfo(true)
            }, {
                onFailure(it)
            }).addTo(compositeDisposable)
    }

    // get current weather from cache db.
    private fun subscribeToCurrentWeatherUpdates() {
        getCurrentWeather(defaultCityInfo.cityId).map { weatherInfo ->
            UICurrentWeather(
                defaultCityInfo.name,
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
                weatherInfo.timeOfData
            )
        }.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                onNewWeather(it)
            }, {
                onFailure(it)
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
