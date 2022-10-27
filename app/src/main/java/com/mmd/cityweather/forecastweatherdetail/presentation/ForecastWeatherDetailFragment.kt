package com.mmd.cityweather.forecastweatherdetail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmd.cityweather.common.presentation.Event
import com.mmd.cityweather.common.presentation.models.UIForecastWeather
import com.mmd.cityweather.currentweather.presentation.CurrentWeatherViewModel
import com.mmd.cityweather.databinding.FragmentForecastWeatherDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForecastWeatherDetailFragment : Fragment() {
    private lateinit var binding: FragmentForecastWeatherDetailBinding
    private lateinit var forecastAdapter: ForecastWeatherAdapter
    private val viewModel: ForecastWeatherViewModel by viewModels()
    private val args: ForecastWeatherDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForecastWeatherDetailBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeToViewStateUpdates()
        requestForecastWeather()
    }

    private fun requestForecastWeather() {
        viewModel.subscribeForecastWeather(args.cityId)
    }

    private fun initView() {
        //  set content's position below status bar.
        val statusBarHeightId =
            resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(statusBarHeightId)
        (binding.recyclerviewForecast.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin =
            statusBarHeight

        forecastAdapter = ForecastWeatherAdapter()
        with(binding.recyclerviewForecast) {
            adapter = forecastAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun subscribeToViewStateUpdates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    displayForecastWeather(it.forecastWeathers)
                    showErrorMessage(it.onFailureMessage)
                }
            }
        }
    }

    private fun showErrorMessage(onFailureMessage: Event<Throwable>?) {
        val message = onFailureMessage?.getContentIfNotHandled() ?: return
        binding.tvErrorMessage.visibility = View.VISIBLE
    }


    private fun displayForecastWeather(weathers: List<UIForecastWeather>?) {
        if (weathers == null) return
        forecastAdapter.updateForecastWeather(weathers)
    }
}
