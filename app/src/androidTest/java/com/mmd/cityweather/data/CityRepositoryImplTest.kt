package com.mmd.cityweather.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.mmd.cityweather.common.data.CityRepositoryImpl
import com.mmd.cityweather.common.data.database.Cache
import com.mmd.cityweather.common.data.database.CityWeatherDatabase
import com.mmd.cityweather.common.data.database.RoomCache
import com.mmd.cityweather.common.data.di.CacheModule
import com.mmd.cityweather.common.data.preferences.Preferences
import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.domain.repositories.CityRepository
import com.mmd.cityweather.data.preferences.FakePreferences
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(CacheModule::class)
class CityRepositoryImplTest {
    private lateinit var cityRepository: CityRepository

    private lateinit var fakePreferences: Preferences

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var cache: Cache

    @Before
    fun setup() {

        hiltRule.inject()

        fakePreferences = FakePreferences()

        cityRepository =
            CityRepositoryImpl(
                cache,
                InstrumentationRegistry.getInstrumentation().context.assets,
                fakePreferences
            )
    }

    @Test
    fun storeCity_success() {
        // Given
        val cityInfoDetail = CityInfoDetail(
            12345,
            "Da Nang",
            16.0,
            103.0,
            "VN"
        )

        runBlocking {
            // When
            cityRepository.insertCity(cityInfoDetail)

            // Then
            val observerTest =
                cityRepository.getCityInformation(cityInfoDetail.cityId).test()

            observerTest.assertNoErrors()
            observerTest.assertNoErrors()
            observerTest.assertValue {
                it.cityId == cityInfoDetail.cityId && it.name == cityInfoDetail.name
            }
        }
    }
}
