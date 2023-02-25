package com.spinoza.bininfotest.data.network

import com.spinoza.bininfotest.domain.model.BinInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{bin}")
    suspend fun getBinInfo(@Path(QUERY_PATH) bin: String): Response<BinInfoDto>

    companion object {
        private const val QUERY_PATH = "bin"
    }
}