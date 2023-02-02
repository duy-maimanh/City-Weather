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

package com.mmd.cityweather.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.mmd.cityweather.common.data.ForecastWeatherRepositoryImpl
import com.mmd.cityweather.common.data.api.CityWeatherApi
import com.mmd.cityweather.common.data.api.model.mappers.ApiForecastMapper
import com.mmd.cityweather.common.data.database.Cache
import com.mmd.cityweather.common.data.database.models.cities.Cities
import com.mmd.cityweather.common.data.di.CacheModule
import com.mmd.cityweather.common.domain.model.ForecastDetail
import com.mmd.cityweather.common.domain.model.ForecastWeatherDetail
import com.mmd.cityweather.common.domain.repositories.ForecastWeatherRepository
import com.mmd.cityweather.data.api.utils.FakeServer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(CacheModule::class)
class ForecastWeatherRepositoryImplTest {

    private val fakeServer = FakeServer()
    private lateinit var forecastWeatherRepository: ForecastWeatherRepository
    private lateinit var api: CityWeatherApi

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var retrofitBuilder: Retrofit.Builder

    @Inject
    lateinit var apiForecastMapper: ApiForecastMapper

    @Inject
    lateinit var cache: Cache

    @Before
    fun setup() {
        fakeServer.start()

        hiltRule.inject()

        api = retrofitBuilder.baseUrl(fakeServer.baseEndpoint).build()
            .create(CityWeatherApi::class.java)

        forecastWeatherRepository = ForecastWeatherRepositoryImpl(
            api,
            apiForecastMapper,
            cache
        )
    }

    @After
    fun finish() {
        fakeServer.shutdown()
    }

    @Test
    fun requestForecastWeather_success() = runBlocking {
        // Given
        val expectedNumberOfWeather = 40
        fakeServer.setPathDispatcher()

        // When
        val forecastWeather = forecastWeatherRepository.requestForecastWeather(
            1,
            18.00,
            136.00
        )

        // Then
        Truth.assertThat(forecastWeather.forecastDetails.size)
            .isEqualTo(expectedNumberOfWeather)
    }

    @Test
    fun insertForecastWeather_success() {
        // Given
        val city = Cities(1, "", 120.0, 108.0, "", false)
        val weatherDetail = ForecastWeatherDetail(
            city.cityId,
            listOf(
                ForecastDetail(
                    10.0,
                    12.0,
                    10.0,
                    "",
                    "Mist",
                    Date(2022, 10, 22),
                    Date(2022, 10, 22)
                ),
                ForecastDetail(
                    20.0,
                    20.0,
                    20.0,
                    "",
                    "Sunny",
                    Date(2022, 10, 23),
                    Date(2022, 10, 23)
                )
            )
        )

        // When
        runBlocking {
            cache.storeCity(city)
            forecastWeatherRepository.storeForecastWeather(weatherDetail)
        }

        // Then
        val testObserver = forecastWeatherRepository.getForecastWeather(city.cityId).test()

        testObserver.assertNoErrors()
        testObserver.assertNotComplete()
        testObserver.assertValue {
            it.cityId == city.cityId && it.forecastDetails.size == 1
        }
    }
}
