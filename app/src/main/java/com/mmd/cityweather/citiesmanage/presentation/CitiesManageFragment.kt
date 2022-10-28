package com.mmd.cityweather.citiesmanage.presentation

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
import com.mmd.cityweather.common.MainActivity
import com.mmd.cityweather.databinding.FragmentCitiesManageBinding

class CitiesManageFragment : Fragment() {
    private lateinit var binding: FragmentCitiesManageBinding
    private lateinit var citiesAdapter: CitiesManageAdapter

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
        citiesAdapter = CitiesManageAdapter()
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
