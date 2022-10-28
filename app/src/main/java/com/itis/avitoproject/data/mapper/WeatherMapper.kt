package com.itis.avitoproject.data.mapper

import com.itis.avitoproject.data.response_list.FeelsLike
import com.itis.avitoproject.data.response_list.Temp
import com.itis.avitoproject.data.response_list.WeatherForDay
import com.itis.avitoproject.data.response_list.WeatherListResponse
import com.itis.avitoproject.data.response_weather.WeatherResponse
import com.itis.avitoproject.domain.entity.CurrentWeather
import com.itis.avitoproject.domain.entity.TimeOfDayTemp
import com.itis.avitoproject.domain.entity.WeatherFullDay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeatherMapper {
    fun mapToCurrentWeather(response: WeatherResponse): CurrentWeather =
        CurrentWeather(
            cityName = response.name,
            windSpeed = String.format("%.1f", response.wind.speed),
            feelsLikeTemp = String.format("%.1f", response.main.feelsLike),
            humidity = response.main.humidity,
            pressure = response.main.pressure,
            temp = String.format("%.1f", response.main.temp),
            minTemp = String.format("%.1f", response.main.tempMin),
            maxTemp = String.format("%.1f", response.main.tempMax),
            weatherDescription = response.weather[0].description,
            weatherIcon = response.weather[0].icon,
            windDirection = when (response.wind.deg) {
                in 0..22 -> "Север"
                in 23..67 -> "Северо-Восток"
                in 68..112 -> "Восток"
                in 113..157 -> "Юго-Восток"
                in 158..202 -> "Юг"
                in 203..247 -> "Юго-Запад"
                in 248..292 -> "Запад"
                in 293..337 -> "Северо-Запад"
                in 337..361 -> "Север"
                else -> "Не определено"
            }
        )

    private fun mapToFullDayWeather(response: WeatherForDay): WeatherFullDay =
        WeatherFullDay(
            windSpeed = String.format("%.1f", response.speed),
            humidity = response.humidity,
            pressure = response.pressure,
            listTimesOfDayTemp = createList(response.temp, response.feelsLike),
            minTemp = String.format("%.1f", response.temp.min),
            maxTemp = String.format("%.1f", response.temp.max),
            weatherIcon = response.weatherIcon[0].icon,
            weatherDescription = response.weatherIcon[0].description,
            date = dateConvert(response.dt),
            windDirection = when (response.deg) {
                in 0..22 -> "Север"
                in 23..67 -> "Северо-Восток"
                in 68..112 -> "Восток"
                in 113..157 -> "Юго-Восток"
                in 158..202 -> "Юг"
                in 203..247 -> "Юго-Запад"
                in 248..292 -> "Запад"
                in 293..337 -> "Северо-Запад"
                in 337..361 -> "Север"
                else -> "Не определено"
            }
        )

    fun mapToFullDayWeatherList(response: WeatherListResponse): List<WeatherFullDay> =
        response.list.map { weather -> mapToFullDayWeather(weather) }


    private fun dateConvert(unix: Int): String {
        val date = Date((unix.toLong()) * 1000)
        val sdf = SimpleDateFormat("dd/MM")
        sdf.timeZone = TimeZone.getDefault()

        return sdf.format(date)

    }

    private fun createList(temp: Temp, feelsLike: FeelsLike): List<TimeOfDayTemp> {
        val list: ArrayList<TimeOfDayTemp> = ArrayList()
        list.add(
            TimeOfDayTemp(
                name = "morn",
                temp = String.format("%.1f", temp.morn),
                feelsLike = String.format("%.1f", feelsLike.morn)
            )
        )
        list.add(
            TimeOfDayTemp(
                name = "day",
                temp = String.format("%.1f", temp.day),
                feelsLike = String.format("%.1f", feelsLike.day)
            )
        )
        list.add(
            TimeOfDayTemp(
                name = "eve",
                temp = String.format("%.1f", temp.eve),
                feelsLike = String.format("%.1f", feelsLike.eve)
            )
        )
        list.add(
            TimeOfDayTemp(
                name = "night",
                temp = String.format("%.1f", temp.night),
                feelsLike = String.format("%.1f", feelsLike.night)
            )
        )
        return list
    }
}
