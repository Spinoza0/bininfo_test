package com.spinoza.bininfotest.domain.usecase

import com.spinoza.bininfotest.domain.repository.BinRepository
import javax.inject.Inject

class IsErrorUseCase @Inject constructor(private val binRepository: BinRepository) {
    operator fun invoke() = binRepository.isError()
}