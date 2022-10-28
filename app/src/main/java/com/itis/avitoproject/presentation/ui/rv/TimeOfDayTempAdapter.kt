package com.itis.avitoproject.presentation.ui.rv

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.itis.avitoproject.domain.entity.TimeOfDayTemp
import com.itis.avitoproject.presentation.ui.diffutil.TimeOfDayTempDiffUtilCallback

class TimeOfDayTempAdapter :
    ListAdapter<TimeOfDayTemp, TimeOfDayTempHolder>(TimeOfDayTempDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeOfDayTempHolder =
        TimeOfDayTempHolder.create(parent)

    override fun onBindViewHolder(holder: TimeOfDayTempHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: TimeOfDayTempHolder,
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

    override fun submitList(list: MutableList<TimeOfDayTemp>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
