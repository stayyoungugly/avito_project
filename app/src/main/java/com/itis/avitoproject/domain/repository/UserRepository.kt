package com.itis.avitoproject.domain.repository

interface UserRepository {

    suspend fun saveCityName(name: String)

    suspend fun getCityName(): String?
}
