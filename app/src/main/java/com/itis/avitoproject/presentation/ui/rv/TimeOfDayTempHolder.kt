package com.itis.avitoproject.presentation.ui.rv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.avitoproject.R
import com.itis.avitoproject.databinding.ItemDayTempBinding
import com.itis.avitoproject.domain.entity.TimeOfDayTemp
import java.sql.Time

class TimeOfDayTempHolder(
    private val binding: ItemDayTempBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var temp: TimeOfDayTemp? = null

    fun bind(item: TimeOfDayTemp) {
        temp = item
        with(binding) {
            tvTemp.text = item.temp + " " + "°C"
            tvFeelsLike.text = item.feelsLike + " " + "°C"
            when (item.name) {
                "morn" -> {
                    tvDaytime.text = "Утро"
                    ivIcon.setImageResource(R.drawable.morn)
                }
                "day" -> {
                    tvDaytime.text = "День"
                    ivIcon.setImageResource(R.drawable.day)
                }
                "eve" -> {
                    tvDaytime.text = "Вечер"
                    ivIcon.setImageResource(R.drawable.evening)
                }
                "night" -> {
                    tvDaytime.text = "Ночь"
                    ivIcon.setImageResource(R.drawable.night)
                }
            }
        }
    }

    fun updateFields(bundle: Bundle) {
        bundle.run {
            getString("TEMP")?.also {
                binding.tvTemp.text = it
            }
            getString("FEELS")?.also {
                binding.tvFeelsLike.text = it
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup
        ) =
            TimeOfDayTempHolder(
                ItemDayTempBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    }
}
