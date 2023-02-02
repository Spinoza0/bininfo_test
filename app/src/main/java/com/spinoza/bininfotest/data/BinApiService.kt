package com.spinoza.bininfotest.data

import com.spinoza.bininfotest.domain.ApiService
import com.spinoza.bininfotest.domain.BinInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface BinApiService : ApiService {
    @GET("{bin}")
    override suspend fun getBinInfo(@Path("bin") bin: String): BinInfo
}