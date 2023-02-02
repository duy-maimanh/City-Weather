/*
 * Developed by 2022 Duy Mai.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mmd.cityweather.common.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.mmd.cityweather.common.data.database.models.cities.Cities
import io.reactivex.Flowable

@Dao
abstract class CitiesDao {
    @Transaction
    @Query("SELECT * FROM cities WHERE cityId = :cityId")
    abstract fun getInfoCity(cityId: Long): Flowable<Cities>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
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
