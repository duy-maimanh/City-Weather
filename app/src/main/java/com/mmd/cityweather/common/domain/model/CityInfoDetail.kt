package com.mmd.cityweather.common.domain.model

data class CityInfoDetail(
    val cityId: Long,
    val name: String,
    val lat: Float,
    val lon: Float,
    val country: String
)
