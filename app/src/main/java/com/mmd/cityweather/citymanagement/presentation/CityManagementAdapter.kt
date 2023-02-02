/*
 * Developed by 2022 Duy Mai.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mmd.cityweather.citymanagement.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmd.cityweather.R
import com.mmd.cityweather.citymanagement.domain.model.UICity
import com.mmd.cityweather.databinding.ItemManageCityBinding

class CityManagementAdapter(
    private val onDelete: (Int, Boolean) -> Unit,
    private val onSelect: (Int) -> Unit
) :
    RecyclerView.Adapter<CityManagementAdapter.CitiesManageViewHolder>() {
    private var isEditMode = false
    private var cities = emptyList<UICity>()

    inner class CitiesManageViewHolder(
        private val binding: ItemManageCityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            // add position of city you want delete
            binding.checkboxRemoveCity.setOnCheckedChangeListener { compoundButton, b ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onDelete.invoke(adapterPosition, b)
                }
            }

            // select position of city you want to see the current weather
            binding.rlManageCity.setOnClickListener {
                if (!isEditMode) {
                    onSelect.invoke(adapterPosition)
                }
            }
        }

        fun bind(city: UICity) {
            // set view
            binding.checkboxRemoveCity.visibility = if (isEditMode) View.VISIBLE else View.GONE
            binding.checkboxRemoveCity.isEnabled = !city.isAuto && !city.isSelected

            binding.tvCannotDeleteCity.visibility =
                if (isEditMode && (city.isAuto || city.isSelected)) View.VISIBLE else View.GONE
            binding.tvCityName.text = city.name
            binding.checkboxRemoveCity.isChecked = city.deleted
            val drawableIcon = if (city.isAuto) {
                R.drawable.ic_baseline_location_on_24
            } else if (city.isSelected) {
                R.drawable.ic_baseline_radio_button_checked_24
            } else {
                0
            }
            binding.tvCityName.setCompoundDrawablesWithIntrinsicBounds(drawableIcon, 0, 0, 0)
        }
    }

    // update the list of city and notify it
    @SuppressLint("NotifyDataSetChanged")
    fun updateListCity(cities: List<UICity>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    // change the status of list city between edit mode and view mode
    @SuppressLint("NotifyDataSetChanged")
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
