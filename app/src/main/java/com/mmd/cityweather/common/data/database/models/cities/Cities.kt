package com.mmd.cityweather.common.data.database.models.cities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmd.cityweather.common.domain.model.CityInfoDetail

@Entity(tableName = "cities")
data class Cities(
    @PrimaryKey
    val cityId: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    val isAuto: Boolean = false
) {
    companion object {
        fun fromDomain(domainModel: CityInfoDetail): Cities {
            return Cities(
                domainModel.cityId,
                domainModel.name,
                domainModel.lat,
                domainModel.lon,
                domainModel.country,
                domainModel.isAuto
            )
        }
    }

    fun toDomain(): CityInfoDetail {
        return CityInfoDetail(cityId, name, latitude, longitude, country, isAuto)
    }
}
