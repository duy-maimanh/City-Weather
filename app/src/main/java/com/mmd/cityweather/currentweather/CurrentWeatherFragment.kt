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
}
