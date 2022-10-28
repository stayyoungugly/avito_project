package com.itis.avitoproject.domain.usecase

import com.itis.avitoproject.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetCityNameUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): String? {
        return withContext(dispatcher) {
            userRepository.getCityName()
        }
    }
}
