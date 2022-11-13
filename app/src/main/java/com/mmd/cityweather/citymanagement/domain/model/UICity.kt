package com.mmd.cityweather.citymanagement.domain.model

data class UICity(
    val id: Long, val name: String, var deleted: Boolean =
        false, val isDefault: Boolean = false
)
