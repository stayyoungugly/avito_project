package com.itis.avitoproject.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentWeather(
    val cityName: String,
    val windSpeed: String,
    val windDirection: String,
    val feelsLikeTemp: String,
    val humidity: Int,
    val pressure: Int,
    val temp: String,
    val minTemp: String,
    val maxTemp: String,
    val weatherDescription: String,
    val weatherIcon: String,
) : Parcelable
