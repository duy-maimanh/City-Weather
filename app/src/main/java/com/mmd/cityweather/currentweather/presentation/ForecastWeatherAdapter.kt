package com.mmd.cityweather.currentweather.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mmd.cityweather.R
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
