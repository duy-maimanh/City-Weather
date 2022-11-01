package com.mmd.cityweather.citymanagement.presentation

import androidx.lifecycle.ViewModel
import com.mmd.cityweather.citymanagement.domain.model.GetListCity
import com.mmd.cityweather.citymanagement.domain.model.UICity
import com.mmd.cityweather.currentweather.presentation.CurrentWeatherViewState
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
class CityManagementViewModel @Inject constructor(
    private val getListCity: GetListCity,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {
    private val _state = MutableStateFlow(CityManagementViewState())
    val state: StateFlow<CityManagementViewState> = _state.asStateFlow()

    init {
        subscribeCities()
    }

    private fun subscribeCities() {
        getListCity.invoke().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                onUpdateCity(it.map { info -> UICity(info.cityId, info.name) })
            }, {

            })
            .addTo(compositeDisposable)
    }

    private fun onUpdateCity(listCity: List<UICity>) {
        _state.update { oldState ->
            oldState.copy(cities = listCity)
        }
    }
}
