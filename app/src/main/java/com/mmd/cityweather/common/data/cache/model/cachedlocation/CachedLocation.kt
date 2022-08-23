package com.mmd.cityweather.common.data.cache.model.cachedlocation

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mmd.cityweather.common.data.cache.model.cacheweather.CachedWeather
import com.mmd.cityweather.common.domain.model.Location


@Entity(
    tableName = "locations",
    foreignKeys = [
        ForeignKey(
            entity = CachedWeather::class,
            parentColumns = ["weatherId"],
            childColumns = ["weatherId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("weatherId")]
)
data class CachedLocation(
    @PrimaryKey(autoGenerate = true)
    var locationId: Int = 0,
    val weatherId: Int,
    val cityName: String,
    val language: String
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
