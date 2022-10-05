package com.mmd.cityweather.common.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mmd.cityweather.common.data.database.models.cities.Cities
import io.reactivex.Flowable

@Dao
abstract class CitiesDao {
    @Transaction
    @Query("SELECT * FROM cities")
    abstract fun getInfoCity(): Flowable<Cities>

    @Insert
    abstract fun insertCity(cities: Cities)

    @Transaction
    @Query("SELECT EXISTS(SELECT * FROM cities)")
    abstract fun isExists(): Boolean
}
