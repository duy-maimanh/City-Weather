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
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.mmd.cityweather.common.data.CityRepositoryImpl
import com.mmd.cityweather.common.data.CurrentWeatherRepositoryImpl
import com.mmd.cityweather.common.data.api.CityWeatherApi
import com.mmd.cityweather.common.data.api.model.mappers.ApiWeatherMapper
import com.mmd.cityweather.common.data.database.Cache
import com.mmd.cityweather.common.data.database.CityWeatherDatabase
import com.mmd.cityweather.common.data.di.CacheModule
import com.mmd.cityweather.common.data.preferences.Preferences
import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.domain.repositories.CityRepository
import com.mmd.cityweather.common.domain.repositories.CurrentWeatherRepository
import com.mmd.cityweather.data.api.utils.FakeServer
import com.mmd.cityweather.data.preferences.FakePreferences
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(CacheModule::class)
class WeatherRepositoryImplTest {

    private val fakeServer = FakeServer()
    private lateinit var testRepository: CurrentWeatherRepository
    private lateinit var cityRepository: CityRepository
    private lateinit var api: CityWeatherApi

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: CityWeatherDatabase

    @Inject
    lateinit var retrofitBuilder: Retrofit.Builder

    @Inject
    lateinit var apiWeatherMapper: ApiWeatherMapper

    @Inject
    lateinit var cache: Cache

    private lateinit var fakePreferences: Preferences

    @Before
    fun setup() {
        fakeServer.start()

        hiltRule.inject()

        fakePreferences = FakePreferences()

        api = retrofitBuilder
            .baseUrl(fakeServer.baseEndpoint)
            .build()
            .create(CityWeatherApi::class.java)

        testRepository =
            CurrentWeatherRepositoryImpl(api, apiWeatherMapper, cache)

        cityRepository = CityRepositoryImpl(
            cache,
            InstrumentationRegistry.getInstrumentation().context.assets,
            fakePreferences
        )
    }

    @After
    fun finish() {
        fakeServer.shutdown()
    }

    @Test
    fun requestCurrentWeather_success() = runBlocking {
        // Given
        val expectedId = 1594018L
        fakeServer.setPathDispatcher()

        // When
        val currentWeather = testRepository.requestNewCurrentWeather(
            1,
            18.00,
            136.00
        )

        // Then
        assertThat(currentWeather.id).isEqualTo(expectedId)
    }

    @Test
    fun insertCurrentWeather_success() {
        // Given
        // expectedId is 1L because this is the first record in the database.
        // We do not use the id that was responded to from the server because it may repeat.
        val expectedId = 1L
        val testCity = CityInfoDetail(
            1234567L,
            "Da Nang",
            16.0,
            103.0,
            "VN"
        )

        runBlocking {
            cityRepository.insertCity(testCity)

            fakeServer.setPathDispatcher()

            val currentWeather = testRepository.requestNewCurrentWeather(
                testCity.cityId,
                18.00,
                136.00
            )

            // When
            testRepository.storeCurrentWeather(currentWeather)
        }

        // Then
        val testObserver =
            testRepository.getCurrentWeather(testCity.cityId).test()

        testObserver.assertNoErrors()
        testObserver.assertNotComplete()
        testObserver.assertValue {
            it.id == expectedId && it.cityId == testCity.cityId
        }
    }
}
