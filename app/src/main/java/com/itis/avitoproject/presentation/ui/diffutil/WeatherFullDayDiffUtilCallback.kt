package com.itis.avitoproject.presentation.ui.diffutil

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.itis.avitoproject.domain.entity.TimeOfDayTemp
import com.itis.avitoproject.domain.entity.WeatherFullDay

class WeatherFullDayDiffUtilCallback : DiffUtil.ItemCallback<WeatherFullDay>() {
    override fun areItemsTheSame(oldItem: WeatherFullDay, newItem: WeatherFullDay) =
        oldItem.date == newItem.date

    override fun areContentsTheSame(oldItem: WeatherFullDay, newItem: WeatherFullDay) =
        oldItem.listTimesOfDayTemp == newItem.listTimesOfDayTemp

    override fun getChangePayload(oldItem: WeatherFullDay, newItem: WeatherFullDay): Any? {
        val bundle = Bundle()
        if (oldItem.minTemp != newItem.minTemp) {
            bundle.putString("MIN", newItem.minTemp)
        }
        if (oldItem.maxTemp != newItem.maxTemp) {
            bundle.putString("MAX", newItem.maxTemp)
        }
        if (oldItem.date != newItem.date) {
            bundle.putString("DT", newItem.date)
        }
        if (oldItem.weatherDescription != newItem.weatherDescription) {
            bundle.putString("DESC", newItem.weatherDescription)
        }
        if (oldItem.weatherIcon != newItem.weatherIcon) {
            bundle.putString("ICON", newItem.weatherIcon)
        }
        if (bundle.isEmpty) return null
        return bundle
    }
}
