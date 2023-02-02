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
import com.mmd.cityweather.common.domain.model.CityCurrentWeatherDetail
import java.util.Date

@Entity(
    tableName = "cached_current_weathers",
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
class CachedCurrentWeathers(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var cityId: Long,
    var conditionId: String,
    var conditionName: String,
    var conditionDescription: String,
    var conditionIcon: String,
    var tempCurrent: Double,
    var tempFeelLike: Double,
    var tempMin: Double,
    var tempMax: Double,
    var pressure: Int,
    var humidity: Int,
    var visibility: Int,
    var windSpeed: Double,
    var windDeg: Int,
    var cloudiness: Int,
    var timeOfData: Date
) {
    companion object {

        fun fromDomain(domainModel: CityCurrentWeatherDetail): CachedCurrentWeathers {
            return CachedCurrentWeathers(
                cityId = domainModel.cityId,
                conditionId = domainModel.conditionId,
                conditionName = domainModel.conditionName,
                conditionDescription = domainModel.conditionDescription,
                conditionIcon = domainModel.conditionIcon,
                tempCurrent = domainModel.temp,
                tempFeelLike = domainModel.tempFeelLike,
                tempMin = domainModel.tempMin,
                tempMax = domainModel.tempMax,
                pressure = domainModel.pressure,
                humidity = domainModel.humidity,
                visibility = domainModel.visibility,
                windSpeed = domainModel.windSpeed,
                windDeg = domainModel.windDeg,
                cloudiness = domainModel.cloudiness,
                timeOfData = domainModel.timeOfData
            )
        }
    }

    fun toDomain(): CityCurrentWeatherDetail {
        return CityCurrentWeatherDetail(
            id,
            cityId,
            conditionId,
            conditionName,
            conditionDescription,
            conditionIcon,
            tempCurrent,
            tempFeelLike,
            tempMin,
            tempMax,
            pressure,
            humidity,
            visibility,
            windSpeed,
            windDeg,
            cloudiness,
            timeOfData
        )
    }
}
