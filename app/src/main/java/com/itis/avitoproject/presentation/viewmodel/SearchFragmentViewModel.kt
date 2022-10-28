package com.itis.avitoproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itis.avitoproject.domain.entity.CurrentWeather
import com.itis.avitoproject.domain.usecase.GetCurrentWeatherByNameUseCase
import com.itis.avitoproject.domain.usecase.SaveCityNameUseCase
import kotlinx.coroutines.launch

class SearchFragmentViewModel(
    private val getCurrentWeatherByNameUseCase: GetCurrentWeatherByNameUseCase,
    private val saveCityNameUseCase: SaveCityNameUseCase
) :
    ViewModel() {

    private val _error: SingleLiveEvent<Throwable> = SingleLiveEvent()
    val error: LiveData<Throwable> = _error

    private val _currentWeather: SingleLiveEvent<Result<CurrentWeather>> = SingleLiveEvent()
    val currentWeather: LiveData<Result<CurrentWeather>> = _currentWeather

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

    fun saveCityName(name: String) {
        viewModelScope.launch {
            try {
                saveCityNameUseCase(name)
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }
}
