package com.mmd.cityweather.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mmd.cityweather.common.data.cache.daos.LocationDao
import com.mmd.cityweather.common.data.cache.model.cachedlocation.CachedLocation
import com.mmd.cityweather.common.data.cache.model.cacheweather.CachedWeather

@Database(
    entities = [
        CachedWeather::class,
        CachedLocation::class
    ],
    version = 1
)
abstract class PetSaveDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
