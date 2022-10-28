package com.itis.avitoproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itis.avitoproject.R
import com.itis.avitoproject.domain.entity.CurrentWeather
import com.itis.avitoproject.domain.entity.WeatherFullDay
import com.itis.avitoproject.domain.usecase.GetCurrentWeatherByCoordinatesUseCase
import com.itis.avitoproject.domain.usecase.GetCurrentWeatherByNameUseCase
import com.itis.avitoproject.domain.usecase.GetDayWeatherByCoordinatesUseCase
import com.itis.avitoproject.domain.usecase.GetDayWeatherByNameUseCase
import kotlinx.coroutines.launch

class CurrentWeatherFragmentViewModel(
    private val getCurrentWeatherByNameUseCase: GetCurrentWeatherByNameUseCase,
    private val getCurrentWeatherByCoordinatesUseCase: GetCurrentWeatherByCoordinatesUseCase,
    private val getDayWeatherByCoordinatesUseCase: GetDayWeatherByCoordinatesUseCase,
    private val getDayWeatherByNameUseCase: GetDayWeatherByNameUseCase
) : ViewModel() {

    private val _error: SingleLiveEvent<Throwable> = SingleLiveEvent()
    val error: LiveData<Throwable> = _error

    private val _currentWeather: MutableLiveData<Result<CurrentWeather>> = MutableLiveData()
    val currentWeather: LiveData<Result<CurrentWeather>> = _currentWeather

    private val _dayWeather: MutableLiveData<Result<WeatherFullDay>> = MutableLiveData()
    val dayWeather: LiveData<Result<WeatherFullDay>> = _dayWeather

    fun getCurrentWeatherByName(name: String) {
        viewModelScope.launch {
            try {
                val weather = getCurrentWeatherByNameUseCase(name)
                _currentWeather.value = Result.success(weather)
            } catch (ex: Exception) {
                _currentWeather.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun getCurrentWeatherByCoordinates(lat: String, lon: String) {
        viewModelScope.launch {
            try {
                val weather = getCurrentWeatherByCoordinatesUseCase(lat, lon)
                _currentWeather.value = Result.success(weather)
            } catch (ex: Exception) {
                _currentWeather.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun getDayWeatherByName(name: String) {
        viewModelScope.launch {
            try {
                val weather = getDayWeatherByNameUseCase(name)
                _dayWeather.value = Result.success(weather)
            } catch (ex: Exception) {
                _dayWeather.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun getDayWeatherByCoordinates(lat: String, lon: String) {
        viewModelScope.launch {
            try {
                val weather = getDayWeatherByCoordinatesUseCase(lat, lon)
                _dayWeather.value = Result.success(weather)
            } catch (ex: Exception) {
                _dayWeather.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

}
