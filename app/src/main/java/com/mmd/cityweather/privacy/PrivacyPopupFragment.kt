package com.mmd.cityweather.privacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mmd.cityweather.databinding.DialogPrivacyBinding

class PrivacyPopupFragment: DialogFragment() {

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
}