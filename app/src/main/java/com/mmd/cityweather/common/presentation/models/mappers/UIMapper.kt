package com.mmd.cityweather.common.presentation.models.mappers

interface UIMapper<E, D> {
    fun mapToUI(domainEntity: E): D
}
