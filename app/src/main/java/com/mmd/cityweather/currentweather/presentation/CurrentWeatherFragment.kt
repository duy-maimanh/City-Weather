package com.mmd.cityweather.currentweather.presentation

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.mmd.cityweather.R
import com.mmd.cityweather.common.MainActivity
import com.mmd.cityweather.common.domain.model.CityInfoDetail
import com.mmd.cityweather.common.presentation.Event
import com.mmd.cityweather.common.presentation.models.UICurrentWeather
import com.mmd.cityweather.common.presentation.models.UIForecastWeather
import com.mmd.cityweather.currentweather.domain.model.CityId
import com.mmd.cityweather.databinding.FragmentCurrentWeatherBinding
import com.mmd.cityweather.forecastweatherdetail.presentation.ForecastWeatherAdapter
import com.mmd.cityweather.privacy.presentation.PrivacyPopupFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {
    private lateinit var binding: FragmentCurrentWeatherBinding
    private val viewModel: CurrentWeatherViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var forecastWeatherAdapter: ForecastWeatherAdapter

    // only work if the device have google play
    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                // Permission granted
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        viewModel.onEven(
                            CurrentWeatherEvent.ChangeNewLocation(
                                location.latitude, location.longitude
                            )
                        )
                    } else {
                        val locationRequest: LocationRequest =
                            LocationRequest().setPriority(
                                LocationRequest.PRIORITY_LOW_POWER
                            ).setInterval(10000).setFastestInterval(
                                1000
                            ).setNumUpdates(1)


                        // Initialize location call back
                        val locationCallback: LocationCallback =
                            object : LocationCallback() {
                                override fun onLocationResult(
                                    locationResult: LocationResult
                                ) {
                                    // Initialize
                                    // location
                                    val location1: Location =
                                        locationResult.lastLocation ?: return
                                    viewModel.onEven(
                                        CurrentWeatherEvent.ChangeNewLocation(
                                            location1.latitude,
                                            location1.longitude
                                        )
                                    )
                                }
                            }
                        // Request location updates
                        fusedLocationClient.requestLocationUpdates(
                            locationRequest, locationCallback, Looper.myLooper()
                        )
                    }
                }.addOnFailureListener { exception ->
                    println()
                }
            } else {
                println()
                // Permission denied
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCurrentWeatherBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkAutoUpdate()
        viewModel.checkRequestPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewSetup()
        subscribeToViewStateUpdates()

        parentFragmentManager.setFragmentResultListener(
            PrivacyPopupFragment.PRIVACY_KEY,
            this
        ) { _, bundle ->
            val result = bundle.getBoolean(PrivacyPopupFragment.APPROVE_LOCATION_KEY)
            if (result) {
                locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
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

        binding.swipeLayout.setOnRefreshListener {
            viewModel.onEven(CurrentWeatherEvent.RequestRecentCurrentWeather)
        }

        forecastWeatherAdapter = ForecastWeatherAdapter()
        with(binding.lnForecast) {
            adapter = forecastWeatherAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.btnForecastDetail.setOnClickListener {
            viewModel.onEven(CurrentWeatherEvent.OpenForecastDetail)
        }
        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.manage_city -> {
                    openManageCitiesFragment()
                    return@setOnMenuItemClickListener true
                }
                R.id.setting -> {
                    openSetting()
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
    }

    private fun openSetting() {
        findNavController().navigate(R.id.action_currentWeatherFragment_to_settingsFragment)
    }

    private fun openManageCitiesFragment() {
        findNavController().navigate(R.id.action_currentWeatherFragment_to_citiesManageFragment)
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
        updateDataToUI(state.weather)
        updateForecastWeatherToUI(state.forecastWeather)
        handleFailures(state.failure)
        handleRequestNewCurrentWeather(state.hasCityInfo, state.isFirstInit)
        updateLoadingStatus(state.loading)
        handleMoveToCorrectLocation(state.moveToCorrectLocation)
        handleOpenForecastWeatherDetail(state.openForecastDetail)
        startUpdateUpdate(state.startAutoUpdate)
        showLocationExplainDialog(state.isShowLocationExplainDialog)
        requestPermissionWhenUserApproveLocationExplain(state.isUserApproveForLocation)
    }

    private fun requestPermissionWhenUserApproveLocationExplain(
        status: Event<Boolean>?
    ) {
        val unhandleStatus = status?.getContentIfNotHandled() ?: return

        if (unhandleStatus) {
            locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }


    private fun showLocationExplainDialog(status: Event<Boolean>?) {
        val unhandleStatus = status?.getContentIfNotHandled() ?: return

        if (unhandleStatus) {
            findNavController().navigate(R.id.action_currentWeatherFragment_to_privacyPopupFragment)
            viewModel.markLocationExplainShowed()
        }
    }

    private fun startUpdateUpdate(isStart: Pair<Boolean, CityInfoDetail>?) {
        val unHandleCityInfo = isStart ?: return

        if (unHandleCityInfo.first) {
            (activity as? MainActivity)?.runWeatherUpdate()
        } else {
            (activity as? MainActivity)?.closeWeatherUpdate()
        }
    }

    private fun handleOpenForecastWeatherDetail(cityIds: List<CityId>) {
        cityIds.firstOrNull()?.let {
            openForecastWeatherDetail(it.cityId)
            viewModel.forecastWeatherDetailOpened(it.id)
        }
    }

    private fun handleMoveToCorrectLocation(status: Boolean) {
        if (status) {
            findNavController().navigate(R.id.action_currentWeatherFragment_to_currentWeatherFragment)
        }
    }

    private fun updateLoadingStatus(status: Boolean) {
        binding.swipeLayout.isRefreshing = status
    }

    private fun handleRequestNewCurrentWeather(
        hasCityInfo: Boolean, isFirstInit: Boolean
    ) {
        // if user have city info and don't init current weather, call it.
        if (hasCityInfo && isFirstInit) {
            requestInitCurrentWeather()
        }
    }

    private fun updateForecastWeatherToUI(
        forecastWeather: List<UIForecastWeather>?
    ) {
        if (forecastWeather == null) return
        forecastWeatherAdapter.updateForecastWeather(forecastWeather)
    }

    private fun updateDataToUI(uiCurrentWeather: UICurrentWeather?) {
        uiCurrentWeather?.let {
            with(binding) {
                coordinatorLayout.background =
                    ContextCompat.getDrawable(requireContext(), it.backgroundId)
                appBar.title = it.cityName
                tvDegree.text = it.temp.roundToInt().toString()
                tvFeelLikeDegree.text = String.format(
                    getString(R.string.tv_temp),
                    it.tempFeelLike.roundToInt().toString()
                )
                tvHumidity.text = String.format(
                    getString(R.string.tv_humidity), it.humidity.toString()
                )
                tvWindSpeed.text = String.format(
                    getString(R.string.tv_wind_speed),
                    it.windSpeed.roundToInt().toString()
                )
                tvCloudiness.text = String.format(
                    getString(R.string.tv_humidity), it.cloudiness.toString()
                )
                tvVisibility.text = String.format(
                    getString(R.string.tv_visibility), it.visibility.toString()
                )
                tvPressure.text = String.format(
                    getString(R.string.tv_pressure), it.pressure.toString()
                )
                tvWeatherDescription.text = it.weatherCondition
                tvCurrentDay.text = it.getTimeOfData()
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
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_LONG)
                .show()
        }
        updateLoadingStatus(false)
    }

    private fun openForecastWeatherDetail(cityId: Long) {
        val action =
            CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToForecastWeatherDetailFragment(
                cityId
            )
        findNavController().navigate(action)
    }
}
