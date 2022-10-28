package com.itis.avitoproject.data.response_list


import com.google.gson.annotations.SerializedName

data class WeatherListResponse(
    @SerializedName("city")
    val city: City,
    @SerializedName("list")
    val list: List<WeatherForDay>,
)
