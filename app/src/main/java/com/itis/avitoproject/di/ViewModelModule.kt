package com.itis.avitoproject.di

import com.itis.avitoproject.presentation.viewmodel.MainFragmentViewModel
import com.itis.avitoproject.presentation.viewmodel.WeekFragmentViewModel
import com.itis.avitoproject.presentation.viewmodel.CurrentWeatherFragmentViewModel
import com.itis.avitoproject.presentation.viewmodel.SearchFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainFragmentViewModel)
    viewModelOf(::WeekFragmentViewModel)
    viewModelOf(::SearchFragmentViewModel)
    viewModelOf(::CurrentWeatherFragmentViewModel)
}
