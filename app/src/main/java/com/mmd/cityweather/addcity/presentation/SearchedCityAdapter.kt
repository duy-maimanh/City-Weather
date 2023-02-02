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

package com.mmd.cityweather.addcity.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmd.cityweather.citymanagement.domain.model.UICity
import com.mmd.cityweather.databinding.ItemSearchedCityBinding

class SearchedCityAdapter(private val onSelectCity: (Long) -> Unit) :
    RecyclerView.Adapter<SearchedCityAdapter.ViewHolder>() {
    private var listCity: List<UICity> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateListCity(listCity: List<UICity>) {
        this.listCity = listCity
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemSearchedCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.tvSearchedCity.setOnClickListener {
                onSelectCity.invoke(listCity[adapterPosition].id)
            }
        }

        fun bind(city: UICity) {
            binding.tvSearchedCity.text = city.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemSearchedCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCity[position])
    }

    override fun getItemCount(): Int {
        return listCity.size
    }
}
