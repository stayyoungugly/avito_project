package com.itis.avitoproject.presentation.ui.diffutil

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.itis.avitoproject.domain.entity.TimeOfDayTemp

class TimeOfDayTempDiffUtilCallback : DiffUtil.ItemCallback<TimeOfDayTemp>() {
    override fun areItemsTheSame(oldItem: TimeOfDayTemp, newItem: TimeOfDayTemp) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: TimeOfDayTemp, newItem: TimeOfDayTemp) =
        oldItem.temp == newItem.temp

    override fun getChangePayload(oldItem: TimeOfDayTemp, newItem: TimeOfDayTemp): Any? {
        val bundle = Bundle()
        if (oldItem.temp != newItem.temp) {
            bundle.putString("TEMP", newItem.temp)
        }
        if (oldItem.feelsLike != newItem.feelsLike) {
            bundle.putString("FEELS", newItem.feelsLike)
        }
        if (bundle.isEmpty) return null
        return bundle
    }
}
