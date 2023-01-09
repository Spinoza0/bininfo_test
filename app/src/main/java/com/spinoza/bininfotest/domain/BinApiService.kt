package com.spinoza.bininfotest.domain

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface BinApiService {
    @GET("{bin}")
    fun getBinInfo(@Path("bin") bin: String): Single<BinInfo>
}