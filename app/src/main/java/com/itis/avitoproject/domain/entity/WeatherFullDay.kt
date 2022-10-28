package com.itis.avitoproject.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherFullDay(
    val windSpeed: String,
    val windDirection: String,
    val humidity: Int,
    val pressure: Int,
    val listTimesOfDayTemp: List<TimeOfDayTemp>,
    val minTemp: String,
    val maxTemp: String,
    val weatherDescription: String,
    val weatherIcon: String,
    val date: String,
) : Parcelable
