package com.mmd.cityweather.currentweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.mmd.cityweather.databinding.FragmentCurrentWeatherBinding
import kotlin.math.roundToInt

class CurrentWeatherFragment : Fragment() {
    private lateinit var binding: FragmentCurrentWeatherBinding

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

        // set content's position below status bar.
        val statusBarHeightId =
            resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(statusBarHeightId)
        (binding.appBarLayout.layoutParams as? MarginLayoutParams)?.topMargin =
            statusBarHeight

        // calculate the alpha of background when user scroll content.
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val toolBarHeight: Int = binding.appBar.measuredHeight
            val appBarHeight = appBarLayout!!.measuredHeight
            val f =
                (appBarHeight.toFloat() - toolBarHeight + verticalOffset) / (appBarHeight.toFloat() - toolBarHeight) * 255
            binding.coordinatorLayout.background.alpha = f.roundToInt()
        })
    }
}
