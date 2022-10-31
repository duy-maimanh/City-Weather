package com.mmd.cityweather.citymanagement.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmd.cityweather.R
import com.mmd.cityweather.databinding.FragmentCitiesManageBinding

class CityManagementFragment : Fragment() {
    private lateinit var binding: FragmentCitiesManageBinding
    private lateinit var citiesAdapter: CityManagementAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCitiesManageBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
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
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        citiesAdapter = CityManagementAdapter()
        val decoration = DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(
            requireContext(),
            R.drawable.manage_cities_divider
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
}
