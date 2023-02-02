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

package com.mmd.cityweather.forecastweatherdetail.presentation

import androidx.lifecycle.ViewModel
import com.mmd.cityweather.common.presentation.Event
import com.mmd.cityweather.common.presentation.models.UIForecastWeather
import com.mmd.cityweather.forecastweatherdetail.domain.ForecastWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ForecastWeatherViewModel @Inject constructor(
    private val forecastWeather: ForecastWeather,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {
    private val _state = MutableStateFlow(ForecastWeatherViewState())
    val state: StateFlow<ForecastWeatherViewState> = _state.asStateFlow()

    fun subscribeForecastWeather(cityId: Long) {
        forecastWeather.invoke(cityId).map { forecastWeather ->
            forecastWeather.forecastDetails.map {
                UIForecastWeather(
                    it.timeOfForeCast,
                    it.conditionIcon,
                    it.conditionDescription,
                    it.minTemp,
                    it.maxTemp,
                    it.temp
                )
            }
        }.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                onNewForecastWeather(it)
            }, {
                onFailure(it)
            }).addTo(compositeDisposable)
    }

    private fun onFailure(throwable: Throwable) {
        _state.update { oldState ->
            oldState.copy(onFailureMessage = Event(throwable))
        }
    }

    private fun onNewForecastWeather(weathers: List<UIForecastWeather>?) {
        _state.update { oldState ->
            oldState.copy(forecastWeathers = weathers)
        }
    }
}
