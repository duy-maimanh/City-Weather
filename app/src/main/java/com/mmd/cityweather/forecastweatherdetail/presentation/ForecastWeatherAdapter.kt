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

package com.mmd.cityweather.forecastweatherdetail.presentation

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mmd.cityweather.R
import com.mmd.cityweather.common.data.api.ApiConstants
import com.mmd.cityweather.common.presentation.models.UIForecastWeather
import com.mmd.cityweather.databinding.ItemForecastWeatherBinding

class ForecastWeatherAdapter :
    RecyclerView.Adapter<ForecastWeatherAdapter.ForecastWeatherViewHolder>() {
    private var weathers = listOf<UIForecastWeather>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateForecastWeather(forecastWeather: List<UIForecastWeather>) {
        weathers = forecastWeather
        notifyDataSetChanged()
    }

    inner class ForecastWeatherViewHolder(
        private val binding: ItemForecastWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: UIForecastWeather) {
            val dateId = weather.getMinimizeDisplayDate()
            binding.forecastDate.text = if (dateId != null) {
                "${binding.root.resources.getString(dateId)} ${weather.getHoursOfForeCast()}"
            } else {
                weather.getFullDisplayDate()
            }
            binding.forecastDescription.text =
                weather.weatherDescription.replaceFirstChar { it.titlecase() }
            binding.forecastTemp.text = String.format(
                binding.root.resources.getString(R.string.tv_forecast_temp),
                weather.minTemp.toInt(),
                weather.maxTemp.toInt()
            )
            Glide.with(binding.root).load(
                "${
                ApiConstants
                    .BASE_IMAGE_URL
                }${weather.icon}${ApiConstants.IMAGE_SUFFIX}"
            ).into(object : CustomTarget<Drawable>(100, 100) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    binding.forecastDescription
                        .setCompoundDrawablesWithIntrinsicBounds(
                            resource,
                            null,
                            null,
                            null
                        )
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    binding.forecastDescription
                        .setCompoundDrawablesWithIntrinsicBounds(
                            placeholder,
                            null,
                            null,
                            null
                        )
                }
            })
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastWeatherViewHolder {
        val binding = ItemForecastWeatherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ForecastWeatherViewHolder,
        position: Int
    ) {
        holder.bind(weathers[position])
    }

    override fun getItemCount(): Int {
        return weathers.size
    }
}
