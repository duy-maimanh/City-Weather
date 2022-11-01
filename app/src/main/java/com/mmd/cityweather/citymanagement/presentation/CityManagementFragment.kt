package com.mmd.cityweather.citymanagement.presentation

import android.os.Bundle
import android.provider.Contacts.Intents.UI
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
import com.mmd.cityweather.currentweather.presentation.CurrentWeatherViewModel
import com.mmd.cityweather.databinding.FragmentCityManagementBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
    }

    private fun initView() {
        val statusBarHeightId =
            resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(statusBarHeightId)
        (binding.toolbarManageCities.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin =
            statusBarHeight

        binding.toolbarManageCities.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbarManageCities.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuEdit -> {
                    citiesAdapter.editEnable(true)
                    showDeleteButton(true)
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        citiesAdapter = CityManagementAdapter()
        val decoration = DividerItemDecoration(
            requireContext(), DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(
            requireContext(), R.drawable.manage_cities_divider
        )?.let {
            decoration.setDrawable(it)
        }
        with(binding.recyclerviewCities) {
            adapter = citiesAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(decoration)
        }
    }

    private fun updateListCity(cities: List<UICity>) {
        citiesAdapter.updateListCity(cities)
    }

    private fun showDeleteButton(isShow: Boolean) {
        binding.btnDeleteCity.visibility =
            if (isShow) View.VISIBLE else View.GONE
    }
}
