package com.spinoza.bininfotest.data

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object BinApiFactory {
    private const val BASE_URL = "https://lookup.binlist.net/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @JvmField
    val apiService: BinApiService = retrofit.create(BinApiService::class.java)
}
