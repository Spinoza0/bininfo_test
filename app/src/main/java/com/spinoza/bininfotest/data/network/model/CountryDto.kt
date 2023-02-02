package com.spinoza.bininfotest.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CountryDto(
    @SerializedName("numeric")
    @Expose
    var numeric: String?,
    @SerializedName("alpha2")
    @Expose
    var alpha2: String?,
    @SerializedName("name")
    @Expose
    var name: String?,
    @SerializedName("emoji")
    @Expose
    var emoji: String?,
    @SerializedName("currency")
    @Expose
    var currency: String?,
    @SerializedName("latitude")
    @Expose
    var latitude: Double?,
    @SerializedName("longitude")
    @Expose
    var longitude: Double?,
)