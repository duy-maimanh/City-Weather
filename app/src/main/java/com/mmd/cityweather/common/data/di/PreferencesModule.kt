package com.mmd.cityweather.common.data.di

import com.mmd.cityweather.common.data.preferences.CityWeatherPreferences
import com.mmd.cityweather.common.data.preferences.Preferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {
    @Binds
    abstract fun bindPreferences(preferences: CityWeatherPreferences): Preferences
}
