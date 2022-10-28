package com.itis.avitoproject.data.database.local

import android.content.SharedPreferences

class PreferenceManager(
    private var sharedPreferences: SharedPreferences
) {

    fun saveCityName(name: String) {
        sharedPreferences.edit().putString(USER_CITY, name).apply()
    }

    fun getCityName(): String? {
        return sharedPreferences.getString(USER_CITY, DEFAULT_VALUE)
    }

    fun deleteCityName() {
        sharedPreferences.edit().putString(USER_CITY, DEFAULT_VALUE).apply()
    }

    companion object {
        private val DEFAULT_VALUE = null
        private const val USER_CITY = "cityName"
    }
}
