package com.mmd.cityweather.currentweather.domain

import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.domain.repositories.CityRepository
import javax.inject.Inject
import kotlin.math.*

class GetCityByLocation @Inject constructor(
    private val cityRepository: CityRepository
) {

    suspend operator fun invoke(lat: Double, lng: Double): CityInfoDetail? {
        return cityRepository.getAllCityInfoOnDisk().minByOrNull {
            findNearestLocation(lat, lng, it.lat, it.lon)
        }
    }

    // https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/
    private fun findNearestLocation(
        lat1: Double, lng1: Double, lat2: Double, lng2: Double
    ): Double {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)

        // convert to radians
        val rLat1 = Math.toRadians(lat1);
        val rLat2 = Math.toRadians(lat2);

        // apply formulae
        val a =
            sin(dLat / 2).pow(2.0) + sin(dLng / 2).pow(2.0) * cos(rLat1) * cos(
                rLat2
            )
        val rad = 6371.0
        val c = 2 * asin(sqrt(a))
        return rad * c
    }
}
