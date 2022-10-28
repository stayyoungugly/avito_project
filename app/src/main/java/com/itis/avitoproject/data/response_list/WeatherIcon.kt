package com.itis.avitoproject.data.response_list


import com.google.gson.annotations.SerializedName

data class WeatherIcon(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
)
