package com.mmd.cityweather.common.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mmd.cityweather.common.data.database.daos.LocationDao
import com.mmd.cityweather.common.data.database.model.cachedcities.CachedCities
import com.mmd.cityweather.common.data.database.model.cacheweather.CachedWeather

@Database(
    entities = [
        CachedWeather::class,
        CachedCities::class
    ],
    version = 1
)
abstract class PetSaveDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
