package com.mmd.cityweather.data.di

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.mmd.cityweather.common.data.database.Cache
import com.mmd.cityweather.common.data.database.CityWeatherDatabase
import com.mmd.cityweather.common.data.database.RoomCache
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
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideCached(database: CityWeatherDatabase): Cache {
        return RoomCache(database.weatherDao(), database.citiesDao())
    }
}
