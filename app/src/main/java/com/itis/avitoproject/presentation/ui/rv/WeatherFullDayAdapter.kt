package com.itis.avitoproject.presentation.ui.rv

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import com.itis.avitoproject.domain.entity.WeatherFullDay
import com.itis.avitoproject.presentation.ui.diffutil.WeatherFullDayDiffUtilCallback

class WeatherFullDayAdapter(
    private val glide: RequestManager,
    private val selectItem: (WeatherFullDay) -> (Unit),
) :
    ListAdapter<WeatherFullDay, WeatherFullDayHolder>(WeatherFullDayDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherFullDayHolder =
        WeatherFullDayHolder.create(parent, glide, selectItem)

    override fun onBindViewHolder(holder: WeatherFullDayHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: WeatherFullDayHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.last().takeIf { it is Bundle }?.let {
                holder.updateFields(it as Bundle)
            }
        }
    }

    override fun submitList(list: MutableList<WeatherFullDay>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
