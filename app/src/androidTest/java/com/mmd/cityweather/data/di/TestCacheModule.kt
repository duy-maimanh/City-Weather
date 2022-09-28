package com.mmd.cityweather.data.di

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.mmd.cityweather.common.data.database.CityWeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TestCacheModule {

    @Provides
    fun provideRoomDatabase(): CityWeatherDatabase {
        return Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            CityWeatherDatabase::class.java
        ).allowMainThreadQueries()
            .build()
    }
}
