package com.spinoza.bininfotest.data

import com.spinoza.bininfotest.domain.ApiService
import com.spinoza.bininfotest.domain.BinInfo
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface BinApiService : ApiService {
    @GET("{bin}")
    override fun getBinInfo(@Path("bin") bin: String): Single<BinInfo>
}