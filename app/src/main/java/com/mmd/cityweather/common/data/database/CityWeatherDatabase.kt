package com.mmd.cityweather.common.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mmd.cityweather.common.data.database.converters.DateConverter
import com.mmd.cityweather.common.data.database.daos.CitiesDao
import com.mmd.cityweather.common.data.database.daos.WeatherDao
import com.mmd.cityweather.common.data.database.models.cacheweather.CachedCurrentWeathers
import com.mmd.cityweather.common.data.database.models.cacheweather.CachedForecastWeathers
import com.mmd.cityweather.common.data.database.models.cities.Cities

@Database(
    entities = [
        Cities::class,
        CachedCurrentWeathers::class,
        CachedForecastWeathers::class
    ],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class CityWeatherDatabase : RoomDatabase() {
    abstract fun citiesDao(): CitiesDao
    abstract fun weatherDao(): WeatherDao
}
