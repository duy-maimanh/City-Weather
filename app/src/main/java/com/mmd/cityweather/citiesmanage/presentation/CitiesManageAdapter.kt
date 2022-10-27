package com.mmd.cityweather.citiesmanage.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmd.cityweather.databinding.ItemManageCityBinding

class CitiesManageAdapter : RecyclerView.Adapter<CitiesManageAdapter.CitiesManageViewHolder>() {

    inner class CitiesManageViewHolder(
        private val binding: ItemManageCityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CitiesManageViewHolder {
        val binding = ItemManageCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CitiesManageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CitiesManageViewHolder,
        position: Int
    ) {
    }

    override fun getItemCount(): Int {
        return 10
    }
}
