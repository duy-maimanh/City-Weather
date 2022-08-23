package com.mmd.cityweather.common.data.cache.model.cacheweather

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weathers")
class CachedWeather(
    @PrimaryKey(autoGenerate = true)
    var weatherId: Int = 0,
    var mainStatus: String,
    var description: String,
    var temp: Float,
    var feelLike: Float,
    var tempMin: Float,
    var tempMax: Float,
    var pressure: Int,
    var humidity: Int,
    var visibility: Int,
    var windSpeed: Float,
    var windDeg: Int,
    var cloudiness: Int,
    var timeCalculate: Long,
)
