package com.mmd.cityweather.common.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DateTimeModule {

    @Provides
    @Singleton
    fun provideCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}
