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
        return cache.getCityInfoById(cityId).map {
            it.toDomain()
        }
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
        return withContext(Dispatchers.IO) {
            var data = listOf<String>()
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
            CityInfoDetail(
                cityId = data[6].toLong(),
                name = data[0],
                lat = data[2].toDouble(),
                lon = data[3].toDouble(),
                country = data[4],
                isAuto = true,
                ascii = data[1]
            )
        }
    }

    override fun setSelectedCity(cityId: Long) {
        cityPreferences.putSelectedCityId(cityId)
    }

    override fun getSelectedCityId(): Long {
       return cityPreferences.getSelectedCityId()
    }

    override fun getSelectedCityInfo(): Flowable<CityInfoDetail> {
        return getCityInformation(getSelectedCityId())
    }

    override suspend fun getAllCityInfoOnDisk(): List<CityInfoDetail> {
        return withContext(Dispatchers.IO) {
            val cityInfoDetails = mutableListOf<CityInfoDetail>()
            assetManager?.open("cities/worldcities.csv")?.let { inputStream ->
                val fr =
                    InputStreamReader(inputStream, Charset.forName("UTF-8"))
                // format city,city_ascii,lat,lng,country,iso2,id
                fr.use {
                    val reader = CSVReader(it)
                    reader.use { r ->
                        // skip the title
                        r.readNext()
                        var line = r.readNext()

                        while (line != null) {
                            cityInfoDetails.add(
                                CityInfoDetail(
                                    cityId = line[6].toLong(),
                                    name = line[0],
                                    lat = line[2].toDouble(),
                                    lon = line[3].toDouble(),
                                    country = line[4],
                                    ascii = line[1]
                                )
                            )
                            line = r.readNext()
                        }
                    }
                }
            }
            cityInfoDetails
        }
    }

    override suspend fun deleteCityById(cityId: Long) {
        withContext(Dispatchers.IO) {
            cache.deleteCityById(cityId)
        }
    }

    override suspend fun deleteCityById(idList: List<Long>) {
        withContext(Dispatchers.IO) {
            cache.deleteCityById(idList)
        }
    }

    override fun subscribeCityInDatabase(): Flowable<List<CityInfoDetail>> {
        return cache.subscribeCityFromDatabase().map { it.map { city -> city.toDomain() } }
    }

    override fun getTopCities(): List<Long> {
        return listOf(
            1840034016,
            1250015082,
            1124469960,
            1392685764,
            1380382862,
            1784736618,
            1036074917,
            1702341327,
            1156228865,
            1300715560,
            1704949870
        )
    }

    override suspend fun getAllCityIdInDatabase(): List<Long> {
       return cache.getAllCityIdInDatabase()
    }

    override suspend fun getAllCityFromDatabase(): List<CityInfoDetail> {
        return cache.getALlCity().map { it.toDomain() }
    }
}
