package com.mmd.cityweather.citymanagement.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmd.cityweather.citymanagement.domain.DeleteCityById
import com.mmd.cityweather.citymanagement.domain.GetListCity
import com.mmd.cityweather.citymanagement.domain.model.UICity
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
class CityManagementViewModel @Inject constructor(
    private val getListCity: GetListCity,
    private val deleteCityById: DeleteCityById,
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

    private fun onEvent(event: CityManagementEvent) {
        when (event) {
            CityManagementEvent.AddCity -> {

            }
            CityManagementEvent.DeleteCity -> {
                deletedCity()
            }
        }
    }

    private fun deletedCity() {
        _state.value.cities.map { it.id }.let { idList ->
            viewModelScope.launch {
                deleteCityById.invoke(idList)
            }
        }
    }

    private fun onUpdateCity(listCity: List<UICity>) {
        _state.update { oldState ->
            oldState.copy(cities = listCity)
        }
    }
}