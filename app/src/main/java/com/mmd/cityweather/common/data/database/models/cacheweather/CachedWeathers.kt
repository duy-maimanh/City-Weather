package com.mmd.cityweather.common.data.database.models.cacheweather

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mmd.cityweather.common.data.database.models.cities.Cities
import com.mmd.cityweather.common.domain.model.CityCurrentWeatherDetail
import java.util.Date

@Entity(
    tableName = "cached_weathers", foreignKeys = [
        ForeignKey(
            entity = Cities::class,
            parentColumns = ["cityId"],
            childColumns = ["cityId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("cityId")]
)
class CachedWeathers(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var cityId: Long,
    var conditionId: String,
    var conditionName: String,
    var conditionDescription: String,
    var conditionIcon: String,
    var temp: Double,
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

        fun fromDomain(domainModel: CityCurrentWeatherDetail): CachedWeathers {
            return CachedWeathers(
                cityId = domainModel.cityId,
                conditionId = domainModel.conditionId,
                conditionName = domainModel.conditionName,
                conditionDescription = domainModel.conditionName,
                conditionIcon = domainModel.conditionIcon,
                temp = domainModel.temp,
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
            temp,
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
