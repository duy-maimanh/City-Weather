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
import androidx.work.*
import com.mmd.cityweather.R
import com.mmd.cityweather.common.workers.UpdateWeatherWorker
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
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
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
    private val updateWeatherTag = "update_weather"
    private val updateWeatherConstraints =
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    private val periodicUpdateWeatherRequest =
        PeriodicWorkRequestBuilder<UpdateWeatherWorker>(
            30, TimeUnit.MINUTES, 5, TimeUnit.MINUTES
        ).addTag(updateWeatherTag).setConstraints(updateWeatherConstraints)
            .build()

    fun runWeatherUpdate() {
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            updateWeatherTag,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicUpdateWeatherRequest
        )
    }

    fun closeWeatherUpdate() {
        WorkManager.getInstance(this).cancelAllWorkByTag(updateWeatherTag)
    }
}
