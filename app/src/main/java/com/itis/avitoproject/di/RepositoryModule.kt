package com.itis.avitoproject.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.itis.avitoproject.domain.repository.UserRepository
import com.itis.avitoproject.data.impl.UserRepositoryImpl
import com.itis.avitoproject.data.mapper.WeatherMapper
import com.itis.avitoproject.data.database.local.PreferenceManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::UserRepositoryImpl) { bind<UserRepository>() }
    factoryOf(::WeatherMapper)
    factoryOf(::PreferenceManager)
}

val sharedPreferencesModule = module {
    single { provideSharedPref(androidApplication()) }
}

fun provideSharedPref(app: Application): SharedPreferences {
    return app.applicationContext.getSharedPreferences(
        "shared_preferences",
        Context.MODE_PRIVATE
    )
}

