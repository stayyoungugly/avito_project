package com.itis.avitoproject.domain.usecase

import com.itis.avitoproject.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SaveCityNameUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(name: String) {
        return withContext(dispatcher) {
            userRepository.saveCityName(name)
        }
    }
}
