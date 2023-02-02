package com.spinoza.bininfotest.domain

interface ApiService {
    suspend fun getBinInfo(bin: String): BinInfo
}