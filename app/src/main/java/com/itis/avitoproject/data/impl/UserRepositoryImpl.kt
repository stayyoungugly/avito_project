package com.itis.avitoproject.data.impl

import com.itis.avitoproject.data.database.local.PreferenceManager
import com.itis.avitoproject.domain.repository.UserRepository

class UserRepositoryImpl(private val preferenceManager: PreferenceManager) : UserRepository {
    override suspend fun saveCityName(name: String) {
        preferenceManager.saveCityName(name)
    }

    override suspend fun getCityName(): String? {
        return preferenceManager.getCityName()
    }


}
