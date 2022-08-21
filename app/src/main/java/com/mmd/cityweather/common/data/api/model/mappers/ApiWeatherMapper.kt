package com.mmd.cityweather.common.data.api.model.mappers

import com.mmd.cityweather.common.data.api.model.WeatherApi
import com.mmd.cityweather.common.domain.model.CurrentWeather

class ApiWeatherMapper : ApiMapper<WeatherApi, CurrentWeather> {
    override fun mapTopDomain(apiEntity: WeatherApi): CurrentWeather {
        TODO("Not yet implemented")
    }
}
