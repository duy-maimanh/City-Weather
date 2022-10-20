package com.mmd.cityweather.common.data.database.models.cacheweather

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mmd.cityweather.common.data.database.models.cities.Cities
import java.util.Date

@Entity(
    tableName = "cached_forecast_weathers", foreignKeys = [
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
}
