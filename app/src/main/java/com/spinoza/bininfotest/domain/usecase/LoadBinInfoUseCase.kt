package com.spinoza.bininfotest.domain.usecase

import com.spinoza.bininfotest.domain.repository.BinRepository
import javax.inject.Inject

class LoadBinInfoUseCase @Inject constructor(private val binRepository: BinRepository) {
    suspend operator fun invoke(binValue: String) = binRepository.loadBinInfo(binValue)
}