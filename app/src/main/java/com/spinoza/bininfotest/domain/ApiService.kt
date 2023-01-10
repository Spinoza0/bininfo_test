package com.spinoza.bininfotest.domain

import io.reactivex.rxjava3.core.Single

interface ApiService {
    fun getBinInfo(bin: String): Single<BinInfo>
}