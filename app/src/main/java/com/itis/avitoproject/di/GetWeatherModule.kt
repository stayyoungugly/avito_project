package com.itis.avitoproject.di

import com.itis.avitoproject.BuildConfig
import com.itis.avitoproject.data.api.OpenWeatherApi
import com.itis.avitoproject.data.impl.WeatherRepositoryImpl
import com.itis.avitoproject.domain.repository.WeatherRepository
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = BuildConfig.API_KEY
private const val QUERY_API_KEY = "appid"
private const val QUERY_UNITS = "units"
private const val UNITS = "metric"
private const val QUERY_LANG = "lang"
private const val LANG = "ru"

val getWeatherModule = module {

    factory(named("Weather")) {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor())
            .addInterceptor(unitsInterceptor())
            .addInterceptor(langInterceptor())
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                    )
                }
            }
            .build()
    }

    factory(named("WeatherRetrofit")) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get(named("Weather")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)
    }

    factory<WeatherRepository> {
        (WeatherRepositoryImpl(
            get(named("WeatherRetrofit")),
            get(),
        ))
    }
}

private fun apiKeyInterceptor() = Interceptor { chain ->
    val original = chain.request()
    val newURL = original.url.newBuilder()
        .addQueryParameter(QUERY_API_KEY, API_KEY)
        .build()
    chain.proceed(
        original.newBuilder()
            .url(newURL)
            .build()
    )
}


private fun unitsInterceptor() = Interceptor { chain ->
    val original = chain.request()
    val newURL: HttpUrl = original.url.newBuilder()
        .addQueryParameter(QUERY_UNITS, UNITS)
        .build()

    chain.proceed(
        original.newBuilder()
            .url(newURL)
            .build()
    )
}

private fun langInterceptor() = Interceptor { chain ->
    val original = chain.request()
    val newURL: HttpUrl = original.url.newBuilder()
        .addQueryParameter(QUERY_LANG, LANG)
        .build()

    chain.proceed(
        original.newBuilder()
            .url(newURL)
            .build()
    )
}
