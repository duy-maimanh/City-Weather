package com.mmd.cityweather.citymanagement.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmd.cityweather.citymanagement.domain.DeleteCityById
import com.mmd.cityweather.citymanagement.domain.SelectedCityById
import com.mmd.cityweather.citymanagement.domain.SubscribeCityFromDatabase
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
    private val subscribeCityFromDatabase: SubscribeCityFromDatabase,
    private val deleteCityById: DeleteCityById,
    private val selectedCityById: SelectedCityById,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {
    private val _state = MutableStateFlow(CityManagementViewState())
    val state: StateFlow<CityManagementViewState> = _state.asStateFlow()

    init {
        subscribeCities()
    }

    private fun subscribeCities() {
        subscribeCityFromDatabase.invoke().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                onUpdateCity(it.map { info ->
                    val isDefault =
                        info.isAuto || selectedCityById.getSelectedCityId() == info.cityId
                    UICity(
                        info.cityId, info.name, isDefault = isDefault
                    )
                })
            }, {

            }).addTo(compositeDisposable)
    }

    fun onEvent(event: CityManagementEvent) {
        when (event) {
            is CityManagementEvent.AddCity -> {

            }
            is CityManagementEvent.DeleteCity -> {
                deletedCity()
            }
            is CityManagementEvent.ChangeMode -> {
                onUpdateEditMode(event.editMode)
            }
            is CityManagementEvent.UpdateDeleteCityList -> {
                _state.value.cities[event.pos].deleted = event.isDelete
            }
            is CityManagementEvent.SelectCity -> {
                selectCity(event.pos)
            }
        }
    }

    // set selected city id to share preferences and move to current weather screen
    private fun selectCity(pos: Int) {
        val cityId = _state.value.cities[pos].id
        selectedCityById.invoke(cityId)
        _state.update { oldState ->
            oldState.copy(moveToCurrentWeather = true)
        }
    }

    // delete all city which mark ready to delete
    private fun deletedCity() {
        _state.value.cities.filter { it.deleted && !it.isDefault }.map { it.id }
            .let { idList ->
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

    private fun onUpdateEditMode(isEditMode: Boolean) {
        _state.update { oldState ->
            oldState.copy(isEdit = isEditMode)
        }
    }
}
