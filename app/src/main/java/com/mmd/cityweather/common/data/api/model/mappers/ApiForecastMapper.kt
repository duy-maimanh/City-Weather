package com.mmd.cityweather.common.data.api.model.mappers

import com.mmd.cityweather.common.data.api.model.ForecastWeatherApi
import com.mmd.cityweather.common.domain.model.ForecastDetail
import com.mmd.cityweather.common.domain.model.ForecastWeatherDetail
import java.util.*
import javax.inject.Inject

class ApiForecastMapper @Inject constructor() :
    ApiMapper<ForecastWeatherApi, ForecastWeatherDetail> {

    override fun mapToDomain(apiEntity: ForecastWeatherApi): ForecastWeatherDetail {
        val updateDate = Date(System.currentTimeMillis())
        return ForecastWeatherDetail(-1L, apiEntity.forecast?.map {
            ForecastDetail(
                it.main?.temp ?: 0.0,
                it.main?.tempMin ?: 0.0,
                it.main?.tempMax ?: 0.0,
                it.weather?.get(0)?.icon ?: "",
                it.weather?.get(0)?.description ?: "",
                Date((it.dt ?: 0) * 1000L),
                updateDate
            )
        } ?: emptyList())
    }
}
