package com.mmd.cityweather.common.data.database.model.cachedcities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mmd.cityweather.common.data.database.model.cacheweather.CachedWeather
import java.util.*

@Entity(
    tableName = "cached_cities",
    foreignKeys = [
        ForeignKey(
            entity = CachedWeather::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("id")]
)
data class CachedCities(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val cityId: Int,
    val dateAdd: Date
) {
    companion object {
//        fun fromDomain(domainModel: Location): CachedLocation {
//            return CachedLocation(
//                cityName = domainModel.name,
//                language = "en"
//            )
//        }
    }
}
