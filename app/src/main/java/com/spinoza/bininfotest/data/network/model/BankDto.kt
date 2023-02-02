package com.spinoza.bininfotest.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BankDto (
    @SerializedName("name")
    @Expose
    var name: String?,
    @SerializedName("url")
    @Expose
    var url: String?,
    @SerializedName("phone")
    @Expose
    var phone: String?,
    @SerializedName("city")
    @Expose
    var city: String?,
)