package com.spinoza.bininfotest.domain.usecase

import com.spinoza.bininfotest.domain.repository.BinRepository
import javax.inject.Inject

class ClearBinsHistoryUseCase @Inject constructor(private val binRepository: BinRepository) {
    suspend operator fun invoke() = binRepository.clearBinsHistory()
}