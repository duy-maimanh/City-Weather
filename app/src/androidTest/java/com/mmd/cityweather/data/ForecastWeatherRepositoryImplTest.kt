package com.mmd.cityweather.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.mmd.cityweather.common.data.CityRepositoryImpl
import com.mmd.cityweather.common.data.CurrentWeatherRepositoryImpl
import com.mmd.cityweather.common.data.ForecastWeatherRepositoryImpl
import com.mmd.cityweather.common.data.api.CityWeatherApi
import com.mmd.cityweather.common.data.api.model.mappers.ApiForecastMapper
import com.mmd.cityweather.common.data.api.model.mappers.ApiWeatherMapper
import com.mmd.cityweather.common.data.di.CacheModule
import com.mmd.cityweather.common.domain.repositories.ForecastWeatherRepository
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

    @Before
    fun setup() {
        fakeServer.start()

        hiltRule.inject()

        api = retrofitBuilder.baseUrl(fakeServer.baseEndpoint).build()
            .create(CityWeatherApi::class.java)

        forecastWeatherRepository = ForecastWeatherRepositoryImpl(
            api, apiForecastMapper
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
        val forecastWeather = forecastWeatherRepository.requestForcastWeather(
            1, 18.00, 136.00
        )

        // Then
        Truth.assertThat(forecastWeather.forecastDetails.size)
            .isEqualTo(expectedNumberOfWeather)
    }
}