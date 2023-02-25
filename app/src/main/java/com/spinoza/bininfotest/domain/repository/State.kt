package com.spinoza.bininfotest.domain.repository

import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.model.BinInfo

sealed class State {

    object Loading : State()
    class Error(val value: String) : State()
    class BinInfoData(val value: BinInfo) : State()
    class BinsHistory(val value: List<Bin>) : State()
}