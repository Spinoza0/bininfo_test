package com.spinoza.bininfotest.data.network

import com.spinoza.bininfotest.domain.model.BinInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface BinApiService {
    @GET("{bin}")
    suspend fun getBinInfo(@Path("bin") bin: String): BinInfo
}