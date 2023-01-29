package com.mmd.cityweather.common.di

import com.mmd.cityweather.common.data.CityRepositoryImpl
import com.mmd.cityweather.common.data.CurrentWeatherRepositoryImpl
import com.mmd.cityweather.common.data.ForecastWeatherRepositoryImpl
import com.mmd.cityweather.common.domain.repositories.CityRepository
import com.mmd.cityweather.common.domain.repositories.CurrentWeatherRepository
import com.mmd.cityweather.common.domain.repositories.ForecastWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    companion object {
        @Provides
        fun provideCompositeDisposable() = CompositeDisposable()
    }
}
