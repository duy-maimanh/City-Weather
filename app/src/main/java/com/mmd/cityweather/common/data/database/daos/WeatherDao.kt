package com.mmd.cityweather.common.data.database.daos

import androidx.room.*
import com.mmd.cityweather.common.data.database.models.cacheweather.CachedCurrentWeathers
import com.mmd.cityweather.common.data.database.models.cacheweather.CachedForecastWeathers
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertForecastWeather(cachedForecastWeathers: List<CachedForecastWeathers>)

    @Transaction
    @Query("SELECT * FROM cached_forecast_weathers WHERE cityId = :cityId AND timeOfUpdate = (SELECT max(timeOfUpdate) from cached_forecast_weathers where cityId = :cityId)")
    abstract fun getForecastWeather(cityId: Long): Flowable<List<CachedForecastWeathers>>
}
