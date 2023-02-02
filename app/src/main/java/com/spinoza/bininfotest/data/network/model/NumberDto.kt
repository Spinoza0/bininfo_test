package com.spinoza.bininfotest.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NumberDto(
    @SerializedName("length")
    @Expose
    var length: Int?,
    @SerializedName("luhn")
    @Expose
    var luhn: Boolean?,
)