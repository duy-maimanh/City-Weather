package com.mmd.cityweather.currentweather.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.mmd.cityweather.R
import com.mmd.cityweather.common.domain.model.CityCurrentWeatherDetail
import com.mmd.cityweather.common.presentation.Event
import com.mmd.cityweather.common.presentation.models.UICurrentWeather
import com.mmd.cityweather.databinding.FragmentCurrentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {
    private lateinit var binding: FragmentCurrentWeatherBinding
    private val viewModel: CurrentWeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCurrentWeatherBinding.inflate(
            inflater, container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewSetup()
        subscribeToViewStateUpdates()
    }

    private fun viewSetup() {
        //  set content's position below status bar.
        val statusBarHeightId =
            resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(statusBarHeightId)
        (binding.appBar.layoutParams as? MarginLayoutParams)?.topMargin =
            statusBarHeight
        var toolbarBottomLineMargin = 0
        (binding.toolBarBottomLine.layoutParams as? MarginLayoutParams)?.let {
            toolbarBottomLineMargin = it.topMargin + statusBarHeight
        }
        (binding.toolBarBottomLine.layoutParams as? MarginLayoutParams)?.topMargin =
            toolbarBottomLineMargin


        // calculate the alpha of background when user scroll content.
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val toolBarHeight: Int = binding.appBar.measuredHeight
            val appBarHeight = appBarLayout!!.measuredHeight
            val screenBackgroundAlpha =
                (appBarHeight.toFloat() - toolBarHeight + verticalOffset) / (appBarHeight.toFloat() - toolBarHeight) * 255
            binding.coordinatorLayout.background.alpha =
                screenBackgroundAlpha.roundToInt()


            val toolbarAlphaStartPosition =
                (appBarHeight.toFloat() - toolBarHeight) * 0.45f
            if (verticalOffset <= -toolbarAlphaStartPosition) {
                val toolbarBackgroundAlpha =
                    ((toolbarAlphaStartPosition + verticalOffset) / toolbarAlphaStartPosition) * 255
                binding.toolBarBottomLine.background.alpha =
                    -toolbarBackgroundAlpha.roundToInt()
            } else {
                binding.toolBarBottomLine.background.alpha = 0
            }
        })
    }

    private fun requestInitCurrentWeather() {
        viewModel.onEven(CurrentWeatherEvent.RequestInitCurrentWeather)
    }

    private fun subscribeToViewStateUpdates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    updateScreenState(it)
                }
            }
        }
    }

    private fun updateScreenState(state: CurrentWeatherViewState) {
        updateDateToUI(state.weather)
        handleFailures(state.failure)
    }

    private fun updateDateToUI(
        uiCurrentWeather:
        UICurrentWeather?
    ) {
        uiCurrentWeather?.let {
            with(binding) {
                appBar.title = it.cityName
                tvDegree.text = it.temp.roundToInt().toString()
                tvFeelLikeDegree.text = String.format(
                    getString(R.string.tv_temp),
                    it.tempFeelLike.roundToInt().toString()
                )
                tvHumidity.text = String.format(
                    getString(R.string.tv_humidity),
                    it.humidity.toString()
                )
                tvWindSpeed.text = String.format(
                    getString(R.string.tv_wind_speed),
                    it.windSpeed.roundToInt().toString()
                )
                tvCloudiness.text = String.format(
                    getString(R.string.tv_humidity),
                    it.cloudiness.toString()
                )
                tvVisibility.text = String.format(
                    getString(R.string.tv_visibility),
                    it.visibility.toString()
                )
                tvPressure.text = String.format(
                    getString(R.string.tv_pressure),
                    it.pressure.toString()
                )
            }
        }
    }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = "An error occurred. Please try again later."
        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        } else {
            unhandledFailure.message!!
        }

        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}