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

package com.mmd.cityweather.splash.presentation

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
