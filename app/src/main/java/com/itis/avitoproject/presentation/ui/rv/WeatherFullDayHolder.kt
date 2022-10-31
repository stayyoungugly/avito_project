package com.itis.avitoproject.presentation.ui.rv

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.itis.avitoproject.R
import com.itis.avitoproject.databinding.ItemDayTempBinding
import com.itis.avitoproject.databinding.ItemWeekTempBinding
import com.itis.avitoproject.domain.entity.WeatherFullDay
import java.sql.Time

class WeatherFullDayHolder(
    private val binding: ItemWeekTempBinding,
    private val glide: RequestManager,
    private val selectItem: (WeatherFullDay) -> (Unit),
) : RecyclerView.ViewHolder(binding.root) {

    private var temp: WeatherFullDay? = null

    init {
        binding.btnMore.setOnClickListener {
            temp?.let { weather -> selectItem(weather) }
        }
    }

    private val options by lazy {
        RequestOptions()
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    fun bind(item: WeatherFullDay) {
        temp = item
        with(binding) {
            tvDate.text = item.date
            tvTempMin.text = item.minTemp + " " + "° "
            tvTempMax.text = "/ " + item.maxTemp + " " + "° "
            tvDescription.text = item.weatherDescription
            glide.load(generateIcon(item.weatherIcon))
                .apply(options)
                .into(ivIcon)
        }
    }

    fun updateFields(bundle: Bundle) {
        bundle.run {
            getString("MIN")?.also {
                binding.tvTempMin.text = it + " " + "° "
            }
            getString("MAX")?.also {
                binding.tvTempMax.text = it + " " + "° "
            }
            getString("DESC")?.also {
                binding.tvDescription.text = it
            }
            getString("DT")?.also {
                binding.tvDate.text = it
            }
            getString("ICON")?.also {
                glide.load(generateIcon(it))
                    .apply(options)
                    .into(binding.ivIcon)
            }
        }
    }

    private fun generateIcon(iconId: String): String {
        val url = "http://openweathermap.org/img/wn/$iconId@2x.png"
        print(url)
        return url
    }

    companion object {
        fun create(
            parent: ViewGroup,
            glide: RequestManager,
            selectItem: (WeatherFullDay) -> (Unit),
        ) =
            WeatherFullDayHolder(
                ItemWeekTempBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                glide,
                selectItem
            )
    }
}
