package com.mmd.cityweather.common.data.di

import android.content.Context
import androidx.room.Room
import com.mmd.cityweather.common.data.database.Cache
import com.mmd.cityweather.common.data.database.RoomCache
import com.mmd.cityweather.common.data.database.CityWeatherDatabase
import com.mmd.cityweather.common.data.database.daos.CitiesDao
import com.mmd.cityweather.common.data.database.daos.WeatherDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): CityWeatherDatabase {
            return Room.databaseBuilder(
                context, CityWeatherDatabase::class
                    .java, "weatherapp.db"
            ).build()
        }

        @Provides
        fun provideCitiesDao(weatherDatabase: CityWeatherDatabase): CitiesDao =
            weatherDatabase.citiesDao()

        @Provides
        fun provideWeathersDao(weatherDatabase: CityWeatherDatabase): WeatherDao =
            weatherDatabase.weatherDao()
    }
}
