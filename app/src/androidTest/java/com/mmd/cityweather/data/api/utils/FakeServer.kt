package com.mmd.cityweather.data.api.utils

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.mmd.cityweather.common.data.api.ApiConstants
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.io.IOException
import java.io.InputStream

class FakeServer {
    companion object {
        private const val TAG = "Fake Server"
    }

    private val mockServer = MockWebServer()
    private val endpointSeparator = "/"
    private val responsesBasePath = "networkresponses/"
    private val currentWeatherEndpointPath =
        endpointSeparator + ApiConstants.CURRENT_WEATHER_ENDPOINT
    private val forecastWeatherEndpointPath =
        endpointSeparator + ApiConstants.FORECAST_WEATHER_ENDPOINT
    private val notFoundResponse = MockResponse().setResponseCode(404)

    val baseEndpoint get() = mockServer.url(endpointSeparator)

    fun start() {
        mockServer.start(8080)
    }

    fun setPathDispatcher() {
        mockServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                val path = request.path ?: return notFoundResponse

                return with(path) {
                    when {
                        startsWith(currentWeatherEndpointPath) -> {
                            MockResponse().setResponseCode(200)
                                .setBody(getJson("${responsesBasePath}current_weather.json"))
                        }
                        startsWith(forecastWeatherEndpointPath) -> {
                            MockResponse().setResponseCode(200)
                                .setBody(
                                    getJson
                                        (
                                        "${responsesBasePath}forecast_weather" +
                                                ".json"
                                    )
                                )
                        }
                        else -> {
                            notFoundResponse
                        }
                    }
                }
            }
        }
    }

    fun shutdown() {
        mockServer.shutdown()
    }

    private fun getJson(path: String): String {
        return try {
            val context = InstrumentationRegistry.getInstrumentation().context
            val jsonStream: InputStream = context.assets.open(path)
            String(jsonStream.readBytes())
        } catch (exception: IOException) {
            Log.e(TAG, "Error reading network response json asset")
            throw exception
        }
    }
}
