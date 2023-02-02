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
                                    getJson(
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
