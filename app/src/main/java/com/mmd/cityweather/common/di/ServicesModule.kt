package com.mmd.cityweather.common.di

import com.mmd.cityweather.common.data.CityRepositoryImpl
import com.mmd.cityweather.common.data.CurrentWeatherRepositoryImpl
import com.mmd.cityweather.common.domain.repositories.CityRepository
import com.mmd.cityweather.common.domain.repositories.CurrentWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
abstract class ServicesModule {

    @Binds
    @ServiceScoped
    abstract fun bindCurrentWeatherRepository(
        repositoryImpl: CurrentWeatherRepositoryImpl
    ): CurrentWeatherRepository

    @Binds
    @ServiceScoped
    abstract fun bindCityRepository(
        repositoryImpl: CityRepositoryImpl
    ): CityRepository
}
