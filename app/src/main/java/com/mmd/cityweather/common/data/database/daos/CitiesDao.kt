package com.mmd.cityweather.common.data.database.daos

import androidx.room.*
import com.mmd.cityweather.common.data.database.models.cities.Cities
import io.reactivex.Flowable

@Dao
abstract class CitiesDao {
    @Transaction
    @Query("SELECT * FROM cities WHERE cityId = :cityId")
    abstract fun getInfoCity(cityId: Long): Flowable<Cities>

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
    abstract fun subscribeCity(): Flowable<List<Cities>>

    @Transaction
    @Query("DELETE FROM cities WHERE cityId in (:idList)")
    abstract fun deleteCity(idList: List<Long>)

    @Transaction
    @Query("SELECT * FROM cities")
    abstract fun getAllCity(): List<Cities>
}