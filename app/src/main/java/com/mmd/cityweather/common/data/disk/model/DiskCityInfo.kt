package com.mmd.cityweather.common.data.disk.model

data class DiskCityInfo(
    val city: String,
    val cityAscii: String,
    val lat: Double,
    val lng: Double,
    val country: String,
    val iso2: String,
    val id: Long
)
