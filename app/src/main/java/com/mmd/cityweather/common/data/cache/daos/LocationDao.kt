package com.mmd.cityweather.common.data.cache.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.mmd.cityweather.common.data.cache.model.cachedlocation.CachedLocation
import io.reactivex.Flowable

@Dao
abstract class LocationDao {
    @Transaction
    @Query("SELECT * FROM locations")
    abstract fun getAllLocation(): Flowable<CachedLocation>
}
