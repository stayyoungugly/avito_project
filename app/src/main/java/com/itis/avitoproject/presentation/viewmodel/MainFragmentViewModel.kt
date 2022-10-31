package com.itis.avitoproject.presentation.viewmodel

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itis.avitoproject.domain.usecase.GetCityNameUseCase
import com.itis.avitoproject.presentation.MyApplication
import kotlinx.coroutines.launch

class MainFragmentViewModel(private val getCityNameUseCase: GetCityNameUseCase) : ViewModel() {
    private val _error: SingleLiveEvent<Throwable> = SingleLiveEvent()
    val error: LiveData<Throwable> = _error

    private val _cityName: SingleLiveEvent<String?> = SingleLiveEvent()
    val cityName: LiveData<String?> = _cityName

    fun getCityName() {
        viewModelScope.launch {
            try {
                val name = getCityNameUseCase()
                _cityName.value = name
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }

    fun isPermissionsAllowed(): Boolean {
        var flag = false
        viewModelScope.launch {
            try {
                MyApplication.appContext.let {
                    flag = (ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED)
                }
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
        return flag
    }
}
