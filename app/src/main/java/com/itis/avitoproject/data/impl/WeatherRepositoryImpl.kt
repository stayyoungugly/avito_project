package com.itis.avitoproject.data.impl

import com.itis.avitoproject.data.api.OpenWeatherApi
import com.itis.avitoproject.data.mapper.WeatherMapper
import com.itis.avitoproject.domain.entity.CurrentWeather
import com.itis.avitoproject.domain.entity.WeatherFullDay
import com.itis.avitoproject.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: OpenWeatherApi,
    private val weatherMapper: WeatherMapper
) : WeatherRepository {

    override suspend fun getCurrentWeatherByName(cityName: String): CurrentWeather {
        val map = mapOf("q" to cityName)
        return weatherMapper.mapToCurrentWeather(api.getCurrentWeather(map))
    }

    override suspend fun getCurrentWeatherByCoordinates(lat: String, lon: String): CurrentWeather {
        val map = mapOf("lat" to lat, "lon" to lon)
        return weatherMapper.mapToCurrentWeather(api.getCurrentWeather(map))
    }

    override suspend fun getDayWeatherByName(cityName: String): WeatherFullDay {
        val map = mapOf("cnt" to "1", "q" to cityName)
        return weatherMapper.mapToFullDayWeatherList(api.getWeatherForWeek(map))[0]
    }

    override suspend fun getDayWeatherByCoordinates(lat: String, lon: String): WeatherFullDay {
        val map = mapOf("cnt" to "1", "lat" to lat, "lon" to lon)
        return weatherMapper.mapToFullDayWeatherList(api.getWeatherForWeek(map))[0]
    }

    override suspend fun getWeekWeatherByName(cityName: String): List<WeatherFullDay> {
        val map = mapOf("cnt" to "7", "q" to cityName)
        return weatherMapper.mapToFullDayWeatherList(api.getWeatherForWeek(map))
    }

}
