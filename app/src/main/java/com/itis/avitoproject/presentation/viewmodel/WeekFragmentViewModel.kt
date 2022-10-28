package com.itis.avitoproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itis.avitoproject.domain.entity.CurrentWeather
import com.itis.avitoproject.domain.entity.WeatherFullDay
import com.itis.avitoproject.domain.usecase.GetWeekWeatherByNameUseCase
import kotlinx.coroutines.launch

class WeekFragmentViewModel(private val getWeekWeatherByNameUseCase: GetWeekWeatherByNameUseCase) :
    ViewModel() {

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    private val _weekWeather: MutableLiveData<Result<List<WeatherFullDay>>> = MutableLiveData()
    val weekWeather: LiveData<Result<List<WeatherFullDay>>> = _weekWeather

    fun getWeekWeatherByName(name: String) {
        viewModelScope.launch {
            try {
                val weather = getWeekWeatherByNameUseCase(name)
                _weekWeather.value = Result.success(weather)
            } catch (ex: Exception) {
                _weekWeather.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }
}
