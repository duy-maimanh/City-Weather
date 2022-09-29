package com.mmd.cityweather.common.data.database.daos

import androidx.room.*
import com.mmd.cityweather.common.data.database.models.cacheweather.CachedWeathers
import io.reactivex.Flowable

@Dao
abstract class WeatherDao {
    @Transaction
    @Query("SELECT * FROM cached_weathers ORDER BY timeOfData DESC LIMIT 1")
    abstract fun getLatestWeather(): Flowable<CachedWeathers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrentWeather(cachedWeathers: CachedWeathers)
}
