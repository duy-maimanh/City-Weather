package com.mmd.cityweather.privacy.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.mmd.cityweather.currentweather.presentation.CurrentWeatherFragment
import com.mmd.cityweather.databinding.DialogPrivacyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyPopupFragment : DialogFragment() {
    companion object {
        const val PRIVACY_KEY = "privacy_key"
        const val APPROVE_LOCATION_KEY = "approve_location_key"
    }

    private val locationExplainViewModel: LocationExplainViewModel by viewModels()

    private lateinit var binding: DialogPrivacyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DialogPrivacyBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAgree.setOnClickListener {
            locationExplainViewModel.approve()
            setFragmentResult(
                PRIVACY_KEY,
                bundleOf(APPROVE_LOCATION_KEY to true)
            )
            dismiss()
        }
        binding.btnDecline.setOnClickListener {
            dismiss()
        }
    }
}
