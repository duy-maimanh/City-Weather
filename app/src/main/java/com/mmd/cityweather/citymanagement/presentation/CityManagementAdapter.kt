package com.mmd.cityweather.citymanagement.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmd.cityweather.databinding.ItemManageCityBinding

class CityManagementAdapter :
    RecyclerView.Adapter<CityManagementAdapter.CitiesManageViewHolder>() {
    private var isEditMode = false

    inner class CitiesManageViewHolder(
        private val binding: ItemManageCityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.checkboxRemoveCity.visibility =
                if (isEditMode) View.VISIBLE else View.GONE
        }
    }

    fun editEnable(enable: Boolean) {
        isEditMode = true
        notifyDataSetChanged()
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
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 10
    }
}
