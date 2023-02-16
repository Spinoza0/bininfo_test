package com.spinoza.bininfotest.domain.usecase

import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.repository.BinRepository
import javax.inject.Inject

class RemoveBinFromHistoryUseCase @Inject constructor(private val binRepository: BinRepository) {
    suspend operator fun invoke(bin: Bin) = binRepository.removeBinFromHistory(bin)
}