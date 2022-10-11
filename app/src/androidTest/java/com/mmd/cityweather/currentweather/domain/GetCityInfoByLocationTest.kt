package com.mmd.cityweather.currentweather.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.mmd.cityweather.common.data.CityRepositoryImpl
import com.mmd.cityweather.common.data.database.Cache
import com.mmd.cityweather.common.data.di.CacheModule
import com.mmd.cityweather.common.data.preferences.Preferences
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
class GetCityInfoByLocationTest {
    private lateinit var cityRepository: CityRepository

    private lateinit var fakePreferences: Preferences

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var cache: Cache

    private lateinit var getCityInfoByLocation: GetCityByLocation

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
        getCityInfoByLocation = GetCityByLocation(cityRepository)
    }

    @Test
    fun storeCity_success() {
        val expectedCity = "Đà Nẵng"
        runBlocking {

            val city = getCityInfoByLocation.invoke(16.0545, 108.0717)
            assert(city != null)
            Truth.assertThat(city!!.name).isEqualTo(expectedCity)
        }
    }
}