package com.mmd.cityweather.splash.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmd.cityweather.splash.domain.CheckAnyCityAvailable
import com.mmd.cityweather.splash.domain.GetDefaultCity
import com.mmd.cityweather.splash.domain.InsertDefaultCity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val cityAvailable: CheckAnyCityAvailable,
    private val getDefaultCity: GetDefaultCity,
    private val insertDefaultCity: InsertDefaultCity
) : ViewModel() {
    private val _hasData = MutableLiveData(false)
    val hasData get() = _hasData
    fun check() {
        // if at least one city is exist. Start weather screen.
        // if not, get the default city from csv file and store it to database.
        viewModelScope.launch {
            if (cityAvailable()) {
                _hasData.postValue(true)
            } else {
                insertDefaultCity(getDefaultCity())
                _hasData.postValue(true)
            }
        }
    }
}
