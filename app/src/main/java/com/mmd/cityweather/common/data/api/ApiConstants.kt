/*
 * Developed by 2022 Duy Mai.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mmd.cityweather.common.data.api

object ApiConstants {
    const val BASE_ENDPOINT = "https://api.openweathermap.org/data/2.5/"
    const val API_KEY = "b770acc1fd179fe7756e31362dc31533"
    const val CURRENT_WEATHER_ENDPOINT = "weather"
    const val FORECAST_WEATHER_ENDPOINT = "forecast"
    const val DEFAULT_UNIT = "metric"
    const val BASE_IMAGE_URL = "http://openweathermap.org/img/wn/"
    const val IMAGE_SUFFIX = "@2x.png"
    const val LICENSE_URL = "https://openweathermap.org/"
}

object ApiParameters {
    const val LAT = "lat"
    const val LONG = "lon"
}
