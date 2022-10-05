package com.mmd.cityweather.common.data

import android.content.res.AssetManager
import com.mmd.cityweather.common.data.database.Cache
import com.mmd.cityweather.common.data.database.models.cities.Cities
import com.mmd.cityweather.common.data.preferences.Preferences
import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.domain.repositories.CityRepository
import com.opencsv.CSVReader
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import java.nio.charset.Charset
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val cache: Cache,
    private val assetManager: AssetManager?,
    private val cityPreferences: Preferences
) : CityRepository {

    override fun getCityInformation(cityId: Long): Flowable<CityInfoDetail> {
        return cache.getCityInfo(cityId).map { it.toDomain() }
    }

    override suspend fun insertCity(city: CityInfoDetail) {
        withContext(Dispatchers.IO) {
            cache.storeCity(Cities.fromDomain(city))
        }
    }

    override suspend fun isExist(): Boolean {
        return withContext(Dispatchers.IO) {
            cache.cityIsExist()
        }
    }

    override suspend fun getDefaultCity(): CityInfoDetail {
        var data = listOf("...", "...", "0", "0", "...", "...", "0")
        withContext(Dispatchers.IO) {
            assetManager?.open("cities/worldcities.csv")?.let { inputStream ->
                val fr =
                    InputStreamReader(inputStream, Charset.forName("UTF-8"))
                // format city,city_ascii,lat,lng,country,iso2,id
                fr.use {
                    val reader = CSVReader(it)
                    reader.use { r ->
                        // skip the title
                        r.readNext()
                        // get the first city
                        r.readNext().let { line ->
                            data = line.toList()
                        }
                    }
                }
            }
        }
        return CityInfoDetail(
            cityId = data[6].toLong(),
            name = data[0],
            lat = data[2].toFloat(),
            lon = data[3].toFloat(),
            country = data[4]
        )
    }

    override fun setSelectedCity(cityId: Long) {
        cityPreferences.putSelectedCityId(cityId)
    }

    override fun getSelectedCityInfo(): Flowable<CityInfoDetail> {
        val id = cityPreferences.getSelectedCityId()
        return getCityInformation(id)
    }
}
