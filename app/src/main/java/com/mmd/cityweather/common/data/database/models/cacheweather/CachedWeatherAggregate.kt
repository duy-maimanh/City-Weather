package com.mmd.cityweather.common.data.database.models.cacheweather

import androidx.room.Embedded
import androidx.room.Relation
import com.mmd.cityweather.common.data.database.models.cities.Cities

class CachedWeatherAggregate (
    @Embedded
    val city: Cities,

    @Relation(
        parentColumn = "cityId",
        entityColumn = "cityId"
    )
    val weathers: List<CachedCurrentWeathers>
)
