package com.mmd.cityweather.common.di

import com.mmd.cityweather.common.data.CityRepositoryImpl
import com.mmd.cityweather.common.data.CurrentWeatherRepositoryImpl
import com.mmd.cityweather.common.data.ForecastWeatherRepositoryImpl
import com.mmd.cityweather.common.domain.repositories.CityRepository
import com.mmd.cityweather.common.domain.repositories.CurrentWeatherRepository
import com.mmd.cityweather.common.domain.repositories.ForecastWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppSingletonModule {

    @Binds
    @Singleton
    abstract fun bindCurrentWeatherRepository(
        repositoryImpl: CurrentWeatherRepositoryImpl
    ): CurrentWeatherRepository

    @Binds
    @Singleton
    abstract fun bindCityRepository(
        repositoryImpl: CityRepositoryImpl
    ): CityRepository

    @Binds
    @Singleton
    abstract fun bindForecastWeatherRepository(
        repositoryImpl: ForecastWeatherRepositoryImpl
    ): ForecastWeatherRepository
}
