package com.mmd.cityweather.common.data.api

object ApiConstants {
    const val BASE_ENDPOINT = "https://api.openweathermap.org/data/2.5/"
    const val KEY = "INSERT_YOUR_KEY_HERE"
    const val SECRET = "INSERT_YOUR_SECRET_HERE"
}

object ApiParameters {
    const val TOKEN_TYPE = "Bearer "
    const val AUTH_HEADER = "Authorization"
    const val GRANT_TYPE_KEY = "grant_type"
    const val GRANT_TYPE_VALUE = "client_credentials"
    const val CLIENT_ID = "client_id"
    const val CLIENT_SECRET = "client_secret"

    const val LAT = "lat"
    const val LONG = "lon"
    const val APIKEY = "appid"

    const val ID = "id"
}
