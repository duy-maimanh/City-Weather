package com.mmd.cityweather.common.data.api.model.mappers

import com.mmd.cityweather.common.data.api.model.WeatherApi
import com.mmd.cityweather.common.domain.model.Weather

class ApiWeatherMapper : ApiMapper<WeatherApi, Weather> {
    override fun mapTopDomain(apiEntity: WeatherApi): Weather {
        TODO("Not yet implemented")
    }
}
