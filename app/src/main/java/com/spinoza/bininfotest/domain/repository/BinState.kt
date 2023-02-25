package com.spinoza.bininfotest.domain.repository

import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.model.BinInfo

sealed class BinState {

    object Loading : BinState()
    class Error(val value: String) : BinState()
    class BinInfoData(val value: BinInfo) : BinState()
    class BinsHistory(val value: List<Bin>) : BinState()
}