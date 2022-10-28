package com.itis.avitoproject.di

import com.itis.avitoproject.domain.usecase.GetCurrentWeatherByCoordinatesUseCase
import com.itis.avitoproject.domain.usecase.GetCurrentWeatherByNameUseCase
import com.itis.avitoproject.domain.usecase.GetDayWeatherByCoordinatesUseCase
import com.itis.avitoproject.domain.usecase.GetDayWeatherByNameUseCase
import com.itis.avitoproject.domain.usecase.GetWeekWeatherByNameUseCase
import com.itis.avitoproject.domain.usecase.SaveCityNameUseCase
import com.itis.avitoproject.domain.usecase.GetCityNameUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetCurrentWeatherByCoordinatesUseCase)
    factoryOf(::GetCurrentWeatherByNameUseCase)
    factoryOf(::GetDayWeatherByCoordinatesUseCase)
    factoryOf(::GetDayWeatherByNameUseCase)
    factoryOf(::GetWeekWeatherByNameUseCase)
    factoryOf(::SaveCityNameUseCase)
    factoryOf(::GetCityNameUseCase)

    factory { Dispatchers.Default }
}
