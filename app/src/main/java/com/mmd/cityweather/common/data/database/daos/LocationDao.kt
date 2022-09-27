package com.mmd.cityweather.common.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.mmd.cityweather.common.data.database.model.cachedcities.CachedCities
import io.reactivex.Flowable

@Dao
abstract class LocationDao {
    @Transaction
    @Query("SELECT * FROM locations")
    abstract fun getAllLocation(): Flowable<CachedCities>
}
