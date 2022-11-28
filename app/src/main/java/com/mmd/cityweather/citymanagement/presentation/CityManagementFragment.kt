package com.mmd.cityweather.citymanagement.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmd.cityweather.R
import com.mmd.cityweather.citymanagement.domain.model.UICity
import com.mmd.cityweather.databinding.FragmentCityManagementBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Written by Mai Manh Duy 2022
 * This class use for manage list of city
 */
@AndroidEntryPoint
class CityManagementFragment : Fragment() {
    private lateinit var binding: FragmentCityManagementBinding
    private lateinit var citiesAdapter: CityManagementAdapter
    private val viewModel: CityManagementViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCityManagementBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeToViewStateUpdates()
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

    private fun updateScreenState(state: CityManagementViewState) {
        updateListCity(state.cities)
        updateEditMode(state.isEdit)
        moveToCurrentWeather(state.moveToCurrentWeather)
    }

    private fun updateEditMode(isEdit: Boolean) {
        citiesAdapter.editEnable(isEdit)
        showDeleteButton(isEdit)
        updateToolbar(isEdit)
        updateFabButton(isEdit)
    }

    // when screen is in edit mode hide fab button
    private fun updateFabButton(isEditMode: Boolean) {
        binding.fab.visibility = if (isEditMode) View.GONE else View.VISIBLE
    }

    private fun moveToCurrentWeather(isMove: Boolean) {
        if (isMove) {
            findNavController().navigate(R.id.action_citiesManageFragment_to_currentWeatherFragment)
        }
    }

    private fun initView() {
        val statusBarHeightId =
            resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(statusBarHeightId)
        (binding.toolbarManageCities.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin =
            statusBarHeight

        citiesAdapter = CityManagementAdapter(onDelete = { position, isDelete ->
            viewModel.onEvent(
                // add city you want delete in to prepare list.
                CityManagementEvent.UpdateDeleteCityList(position, isDelete)
            )
        }, onSelect = { position ->
            viewModel.onEvent(
                // select city you want to see the current weather and move to current weather screen
                CityManagementEvent.SelectCity(position)
            )
        })

        // create decoration what use for add space between each item of recyclerview
        val decoration = DividerItemDecoration(
            requireContext(), DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(
            requireContext(), R.drawable.manage_cities_divider
        )?.let {
            decoration.setDrawable(it)
        }

        // init recyclerview
        with(binding.recyclerviewCities) {
            adapter = citiesAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(decoration)
        }

        binding.fab.setOnClickListener {
            // Show bottom sheet where user can add city in to city manage list
            findNavController().navigate(R.id.action_citiesManageFragment_to_addCityBottomSheet)
        }
    }

    private fun updateListCity(cities: List<UICity>) {
        citiesAdapter.updateListCity(cities)
    }

    private fun showDeleteButton(isShow: Boolean) {
        binding.btnDeleteCity.visibility =
            if (isShow) View.VISIBLE else View.GONE
    }

    // Setup toolbar
    private fun updateToolbar(isEditMode: Boolean) {
        // update title toolbar
        binding.toolbarManageCities.title =
            getString(if (isEditMode) R.string.delete_title else R.string.management_city_title)
        // update navigation icon
        binding.toolbarManageCities.setNavigationIcon(
            if (isEditMode) R.drawable.ic_baseline_close_24 else R.drawable.ic_baseline_arrow_back_24
        )
        binding.toolbarManageCities.setNavigationOnClickListener(null)
        binding.toolbarManageCities.setNavigationOnClickListener {
            if (isEditMode) {
                viewModel.onEvent(CityManagementEvent.ChangeMode(false))
            } else {
                findNavController().popBackStack()
            }
        }

        binding.toolbarManageCities.menu.clear()
        binding.toolbarManageCities.setOnMenuItemClickListener(null)
        if (isEditMode) {
            binding.toolbarManageCities.dismissPopupMenus()
            binding.toolbarManageCities.menu.clear()
        } else {
            binding.toolbarManageCities.inflateMenu(R.menu.city_management_tool_bar)
        }

        binding.toolbarManageCities.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuEdit -> {
                    viewModel.onEvent(CityManagementEvent.ChangeMode(editMode = true))
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        binding.btnDeleteCity.setOnClickListener {
            viewModel.onEvent(CityManagementEvent.DeleteCity)
        }
    }
}
