package com.itis.avitoproject.domain.usecase

import com.itis.avitoproject.domain.entity.CurrentWeather
import com.itis.avitoproject.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCurrentWeatherByCoordinatesUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(lat: String, lon: String): CurrentWeather {
        return withContext(dispatcher) {
            weatherRepository.getCurrentWeatherByCoordinates(lat, lon)
        }
    }
}
