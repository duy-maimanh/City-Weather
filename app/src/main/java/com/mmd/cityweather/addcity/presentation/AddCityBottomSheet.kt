package com.mmd.cityweather.addcity.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.mmd.cityweather.R
import com.mmd.cityweather.citymanagement.domain.model.UICity
import com.mmd.cityweather.databinding.BottomSheetAddCityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddCityBottomSheet : BottomSheetDialogFragment() {
    private val viewModel: AddCityBottomSheetViewModel by viewModels()
    private lateinit var binding: BottomSheetAddCityBinding

    companion object {
        const val TAG = "AddCityBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetAddCityBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewStateUpdates()
        viewModel.onEvent(AddCityBottomSheetEvent.LoadTopCities)
    }

    private fun subscribeToViewStateUpdates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    updateTopCityList(it.topCity)
                }
            }
        }
    }

    private fun updateTopCityList(cities: List<UICity>) {
        cities.forEach { city ->
            val chip = Chip(requireContext())
            chip.text = city.name
            chip.setChipBackgroundColorResource(R.color.white)
            chip.isCloseIconVisible = false
            chip.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.bg_bottom_sheet_add_city
                )
            )
            chip.isCheckable = true
            binding.chipCities.addView(chip)
        }
        binding.chipCities.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                viewModel.onEvent(AddCityBottomSheetEvent.AddCity(checkedIds[0]))
            }
        }
    }
}
