package com.mmd.cityweather.addcity.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.mmd.cityweather.R
import com.mmd.cityweather.citymanagement.domain.model.UICity
import com.mmd.cityweather.common.presentation.Event
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { parent ->
                val behaviour = BottomSheetBehavior.from(parent)
                setupFullHeight(parent)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
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
                    addCityDone(it.addCityDone)
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
        binding.chipCities.setOnCheckedStateChangeListener { chipGroup, checkedIds ->
            var pos = 0
            for (i in 0 until chipGroup.childCount) {
                val chip = chipGroup.getChildAt(i)
                if (chipGroup.checkedChipId == chip.id) {
                    pos = i
                }
            }
            if (checkedIds.isNotEmpty()) {
                viewModel.onEvent(AddCityBottomSheetEvent.AddCity(pos))
            }
        }
    }

    private fun addCityDone(event: Event<Boolean>?) {
        val unhandledAction = event?.getContentIfNotHandled() ?: return
        if (unhandledAction) {
            findNavController().popBackStack()
        }
    }
}
