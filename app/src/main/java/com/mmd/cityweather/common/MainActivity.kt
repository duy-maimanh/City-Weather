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

package com.mmd.cityweather.common

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mmd.cityweather.R
import com.mmd.cityweather.common.workers.UpdateWeatherWorker
import com.mmd.cityweather.common.workers.WorkerSettings
import com.mmd.cityweather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var hasData = false

    override fun onCreate(savedInstanceState: Bundle?) {
        // Set it to hide status bar background.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        // Icons set by the Splashscreen API do not work when starting an app from Android Studio. If the app gets closed and then restarted, the icon shows correctly.
        // refer https://stackoverflow.com/questions/69656270/splashscreen-api-not-showing-icon
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applyInsetter {
            type(navigationBars = true) {
                padding()
            }
        }
        setupSplashScreen(splashScreen)
    }

    private fun setupSplashScreen(splashScreen: SplashScreen) {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object :
                ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (hasData) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        val navHostFragment =
                            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
                        navHostFragment.navController.navigate(R.id.action_splashFragment_to_currentWeatherFragment)
                        true
                    } else {
                        false
                    }
                }
            })
    }

    fun updateDataStatus(status: Boolean) {
        hasData = status
    }

    // only run worker if the network status is connected.
    private val updateWeatherConstraints =
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    private val periodicUpdateWeatherRequest =
        PeriodicWorkRequestBuilder<UpdateWeatherWorker>(
            WorkerSettings.UPDATE_WEATHER_TIME,
            TimeUnit.MINUTES,
            WorkerSettings.WORKER_DEFAULT_FLEXTIME,
            TimeUnit.MINUTES
        ).addTag(WorkerSettings.UPDATE_WEATHER_TAG_NAME)
            .setConstraints(updateWeatherConstraints)
            .build()

    fun runWeatherUpdate() {
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            WorkerSettings.UPDATE_WEATHER_TAG_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicUpdateWeatherRequest
        )
    }

    fun closeWeatherUpdate() {
        WorkManager.getInstance(this)
            .cancelAllWorkByTag(WorkerSettings.UPDATE_WEATHER_TAG_NAME)
    }
}
