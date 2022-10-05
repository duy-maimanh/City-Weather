package com.mmd.cityweather.common.data.database.models.cities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmd.cityweather.common.domain.model.CityInfoDetail

@Entity(tableName = "cities")
data class Cities(
    @PrimaryKey
    val cityId: Long,
    val name: String,
    val latitude: Float,
    val longitude: Float,
    val country: String
) {
    companion object {
        fun fromDomain(domainModel: CityInfoDetail): Cities {
            return Cities(
                domainModel.cityId,
                domainModel.name,
                domainModel.lat,
                domainModel.lon,
                domainModel.country
            )
        }
    }

    fun toDomain(): CityInfoDetail {
        return CityInfoDetail(cityId, name, latitude, longitude, country)
    }
}
