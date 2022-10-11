package com.mmd.cityweather.splash.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mmd.cityweather.common.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Objects

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private val viewModel: SplashViewModel by viewModels()
    private val observer = Observer<Boolean> { t -> (activity as? MainActivity)?.updateDataStatus(t) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // because there is no view, so we can't use viewLifecycleOwner
        viewModel.hasData.observeForever(observer)
        viewModel.check()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.hasData.removeObserver(observer)
    }
}
