package com.mmd.cityweather.common.data.database.daos

import androidx.room.*
import com.mmd.cityweather.common.data.database.models.cacheweather.CachedCurrentWeathers
import io.reactivex.Flowable

@Dao
abstract class WeatherDao {
    @Transaction
    @Query(
        "SELECT * FROM cached_current_weathers WHERE cityId IN(:cityId)  ORDER BY " +
                "timeOfData DESC LIMIT 1"
    )
    abstract fun getLatestWeather(cityId: Long): Flowable<CachedCurrentWeathers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrentWeather(cachedCurrentWeathers: CachedCurrentWeathers)
}
