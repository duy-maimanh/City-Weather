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

package com.mmd.cityweather.common.data.database.models.cacheweather

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mmd.cityweather.common.data.database.models.cities.Cities
import com.mmd.cityweather.common.domain.model.ForecastDetail
import java.util.Date

@Entity(
    tableName = "cached_forecast_weathers",
    foreignKeys = [
        ForeignKey(
            entity = Cities::class,
            parentColumns = ["cityId"],
            childColumns = ["cityId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("cityId")]
)
data class CachedForecastWeathers(
    val cityId: Long,
    val timeOfForecasted: Date,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double,
    val description: String,
    val timeOfUpdate: Date,
    val icon: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {

        fun fromDomain(
            cityId: Long,
            domainModel: ForecastDetail
        ): CachedForecastWeathers {
            return CachedForecastWeathers(
                cityId,
                domainModel.timeOfForeCast,
                domainModel.temp,
                domainModel.maxTemp,
                domainModel.minTemp,
                domainModel.conditionDescription,
                domainModel.timeOfUpdate,
                domainModel.conditionIcon
            )
        }
    }

    fun toDomain(): ForecastDetail {
        return ForecastDetail(
            temp,
            tempMin,
            tempMax,
            icon,
            description,
            timeOfForecasted,
            timeOfUpdate
        )
    }
}
