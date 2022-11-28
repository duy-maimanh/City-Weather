package com.mmd.cityweather.common

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.mmd.cityweather.R
import com.mmd.cityweather.common.services.UpdateWeatherService
import com.mmd.cityweather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter


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
                    navHostFragment.navController
                        .navigate(R.id.action_splashFragment_to_currentWeatherFragment)
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


    private var updateWeatherService: UpdateWeatherService? = null
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as UpdateWeatherService.LocalBinder
            updateWeatherService = binder.getService()
            updateWeatherService?.startUpdate()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    fun runWeatherUpdate() {
        if (!mBound) {
            Intent(this, UpdateWeatherService::class.java).also { intent ->
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    fun closeWeatherUpdate() {
        if (mBound) {
            updateWeatherService?.stopUpdate()
            unbindService(connection)
            mBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        closeWeatherUpdate()
    }
}
