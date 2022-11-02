package com.mmd.cityweather.common.domain.model

data class CityInfoDetail(
    val cityId: Long,
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val isAuto: Boolean = false,
    val ascii: String? = null
)
