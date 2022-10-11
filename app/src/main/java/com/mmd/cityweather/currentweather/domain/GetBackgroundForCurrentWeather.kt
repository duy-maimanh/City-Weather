package com.mmd.cityweather.currentweather.domain

import com.mmd.cityweather.R
import java.util.Calendar
import javax.inject.Inject

class GetBackgroundForCurrentWeather @Inject constructor() {

    operator fun invoke(conditionId: Int): Int {
        when (// group 2xx is thunderstorm
            conditionId) {
            in 200 until 299 -> {
                return R.drawable.bg_rain_thunderstorm
            }
            // group 3xx is drizzle
            in 300 until 399 -> {
                return R.drawable.bg_drizzle_snow
            }
            // group 5xx is rain
            in 500 until 599 -> {
                return R.drawable.bg_rain_thunderstorm
            }
            // group 6xx is atmosphere
            in 600 until 799 -> {
                return R.drawable.bg_drizzle_snow
            }
            else -> {
                return when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
                    in 5 until 7 -> {
                        R.drawable.bg_sun_rise
                    }
                    in 16 until 19 -> {
                        R.drawable.bg_sunset
                    }
                    else -> {
                        R.drawable.bg_default_clear_sky
                    }
                }
            }
        }
    }
}
