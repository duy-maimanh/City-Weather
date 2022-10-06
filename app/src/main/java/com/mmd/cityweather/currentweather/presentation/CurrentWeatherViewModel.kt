package com.mmd.cityweather.currentweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.presentation.Event
import com.mmd.cityweather.common.presentation.models.UICurrentWeather
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
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {
    private val _state = MutableStateFlow(CurrentWeatherViewState())
    val state: StateFlow<CurrentWeatherViewState> = _state.asStateFlow()
    private lateinit var cityInfo: CityInfoDetail

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
        }
    }

    private fun requestCurrentWeather() {
        onLoadingStatus(true)
        viewModelScope.launch {
            requestCurrentWeather(
                cityInfo.cityId, cityInfo.lat, cityInfo.lon
            )
        }
    }

    private fun subscribeToSelectedCity() {
        getSelectedCityInfo().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                cityInfo = it
                subscribeToCurrentWeatherUpdates()
                onHasCityInfo(true)
            }, {
                onFailure(it)
            }).addTo(compositeDisposable)
    }

    // get current weather from cache db.
    private fun subscribeToCurrentWeatherUpdates() {
        getCurrentWeather(cityInfo.cityId).map { weatherInfo ->
            UICurrentWeather(
                cityInfo.name,
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
