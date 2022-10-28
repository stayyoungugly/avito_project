package com.itis.avitoproject.data.api

import com.itis.avitoproject.data.response_list.WeatherListResponse
import com.itis.avitoproject.data.response_weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface OpenWeatherApi {
    @GET("forecast/daily")
    suspend fun getWeatherForWeek(@QueryMap options: Map<String, String>): WeatherListResponse

    @GET("weather")
    suspend fun getCurrentWeather(@QueryMap options: Map<String, String>): WeatherResponse
}
