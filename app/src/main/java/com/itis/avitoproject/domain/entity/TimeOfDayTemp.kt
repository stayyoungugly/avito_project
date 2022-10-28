package com.itis.avitoproject.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class TimeOfDayTemp(
    val name: String,
    val temp: String,
    val feelsLike: String
) : Parcelable
