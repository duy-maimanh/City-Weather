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
