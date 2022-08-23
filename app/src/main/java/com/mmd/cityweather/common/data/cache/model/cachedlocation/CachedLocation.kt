package com.mmd.cityweather.common.data.cache.model.cachedlocation

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmd.cityweather.common.domain.model.Location


@Entity(tableName = "locations")
data class CachedLocation(
    @PrimaryKey(autoGenerate = true)
    val locationId: Int = 0,
    val cityName: String,
    val language: String
) {
    companion object {
        fun fromDomain(domainModel: Location): CachedLocation {
            return CachedLocation(
                cityName = domainModel.name,
                language = "en"
            )
        }
    }
}
