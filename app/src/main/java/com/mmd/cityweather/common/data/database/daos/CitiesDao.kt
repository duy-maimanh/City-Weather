package com.mmd.cityweather.common.data.database.daos

import androidx.room.*
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

    @Transaction
    @Query("DELETE FROM cities WHERE cityId = :cityId")
    abstract fun delete(cityId: Long)

    @Transaction
    @Query("SELECT * FROM cities")
    abstract fun getAllCity(): Flowable<List<Cities>>

    @Transaction
    @Query("DELETE FROM cities WHERE cityId in (:idList)")
    abstract fun deleteCity(idList: List<Long>)
}
