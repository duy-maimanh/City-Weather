package com.mmd.cityweather.currentweather.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmd.cityweather.common.domain.model.CityCurrentWeatherDetail
import com.mmd.cityweather.common.presentation.Event
import com.mmd.cityweather.currentweather.domain.GetCurrentWeather
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
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeather: GetCurrentWeather,
    private val requestCurrentWeather: RequestCurrentWeather,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {
    private val _state = MutableStateFlow(CurrentWeatherViewState())
    val state: StateFlow<CurrentWeatherViewState> = _state.asStateFlow()

    init {
        subscribeToCurrentWeatherUpdates()
    }

    fun onEven(event: CurrentWeatherEvent) {
        when (event) {
            is CurrentWeatherEvent.RequestInitCurrentWeather -> {
                requestCurrentWeather()
            }
            is CurrentWeatherEvent.RequestRecentCurrentWeather -> {

            }
        }
    }

    private fun requestCurrentWeather() {
        viewModelScope.launch {
            requestCurrentWeather(1392685764L, 16.0472f, 108.219955f)
        }
    }

    private fun subscribeToCurrentWeatherUpdates() {
        getCurrentWeather(1392685764L)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onNewWeather(it)
            }, {
                onFailure(it)
            }).addTo(compositeDisposable)
    }

    private fun onNewWeather(weather: CityCurrentWeatherDetail) {
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
