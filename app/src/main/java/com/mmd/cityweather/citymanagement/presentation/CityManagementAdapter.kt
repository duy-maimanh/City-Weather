package com.mmd.cityweather.citymanagement.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmd.cityweather.citymanagement.domain.model.UICity
import com.mmd.cityweather.databinding.ItemManageCityBinding

class CityManagementAdapter(private val onDelete: (Int, Boolean) -> Unit) :
    RecyclerView.Adapter<CityManagementAdapter.CitiesManageViewHolder>() {
    private var isEditMode = false
    private var cities = emptyList<UICity>()

    inner class CitiesManageViewHolder(
        private val binding: ItemManageCityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.checkboxRemoveCity.setOnCheckedChangeListener { compoundButton, b ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onDelete.invoke(adapterPosition, b)
                }
            }
        }

        fun bind(city: UICity) {
            binding.checkboxRemoveCity.visibility =
                if (isEditMode && !city.isDefault) View.VISIBLE else View.GONE
            binding.tvCityName.text = city.name
            binding.checkboxRemoveCity.isChecked = city.deleted
        }
    }

    fun updateListCity(cities: List<UICity>) {
        this.cities = cities
        notifyDataSetChanged()
    }


    fun editEnable(enable: Boolean) {
        isEditMode = enable
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
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int {
        return cities.size
    }
}
