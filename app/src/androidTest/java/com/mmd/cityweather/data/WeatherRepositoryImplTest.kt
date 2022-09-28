package com.mmd.cityweather.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mmd.cityweather.common.data.CityRepositoryImpl
import com.mmd.cityweather.common.data.CurrentWeatherRepositoryImpl
import com.mmd.cityweather.common.data.api.CityWeatherApi
import com.mmd.cityweather.common.data.api.model.mappers.ApiWeatherMapper
import com.mmd.cityweather.common.data.database.Cache
import com.mmd.cityweather.common.data.database.CityWeatherDatabase
import com.mmd.cityweather.common.data.database.RoomCache
import com.mmd.cityweather.common.data.di.CacheModule
import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.domain.repositories.CityRepository
import com.mmd.cityweather.common.domain.repositories.CurrentWeatherRepository
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
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(CacheModule::class)
class WeatherRepositoryImplTest {

    private val fakeServer = FakeServer()
    private lateinit var testRepository: CurrentWeatherRepository
    private lateinit var cityRepository: CityRepository
    private lateinit var api: CityWeatherApi
    private lateinit var cache: Cache

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

    @Before
    fun setup() {
        fakeServer.start()

        hiltRule.inject()

        api = retrofitBuilder
            .baseUrl(fakeServer.baseEndpoint)
            .build()
            .create(CityWeatherApi::class.java)

        cache = RoomCache(database.weatherDao(), database.citiesDao())

        testRepository =
            CurrentWeatherRepositoryImpl(api, apiWeatherMapper, cache)
        cityRepository = CityRepositoryImpl(cache)
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
            1, 18.00f, 136.00f
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
            16.0f,
            103.0f,
            "VN"
        )

        runBlocking {
            cityRepository.insertCity(testCity)

            fakeServer.setPathDispatcher()

            val currentWeather = testRepository.requestNewCurrentWeather(
                testCity.cityId, 18.00f, 136.00f
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