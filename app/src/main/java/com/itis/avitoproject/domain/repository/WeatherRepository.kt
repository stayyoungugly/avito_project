package com.itis.avitoproject.domain.repository

import com.itis.avitoproject.domain.entity.CurrentWeather
import com.itis.avitoproject.domain.entity.WeatherFullDay

interface WeatherRepository {

    suspend fun getCurrentWeatherByName(cityName: String): CurrentWeather

    suspend fun getCurrentWeatherByCoordinates(lat: String, lon: String): CurrentWeather

    suspend fun getDayWeatherByName(cityName: String): WeatherFullDay

    suspend fun getDayWeatherByCoordinates(lat: String, lon: String): WeatherFullDay

    suspend fun getWeekWeatherByName(cityName: String): List<WeatherFullDay>
}
