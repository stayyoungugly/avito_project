package com.itis.avitoproject.presentation

import android.app.Application
import android.content.Context
import com.itis.avitoproject.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            allowOverride(false)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    viewModelModule,
                    useCaseModule,
                    getWeatherModule,
                    repositoryModule,
                    sharedPreferencesModule
                )
            )
        }
    }
}
