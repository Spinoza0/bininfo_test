package com.spinoza.bininfotest.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BinInfoDto(
    @SerializedName("number")
    @Expose
    var number: NumberDto?,
    @SerializedName("scheme")
    @Expose
    var scheme: String?,
    @SerializedName("type")
    @Expose
    var type: String?,
    @SerializedName("brand")
    @Expose
    var brand: String?,
    @SerializedName("prepaid")
    @Expose
    var prepaid: Boolean?,
    @SerializedName("country")
    @Expose
    var country: CountryDto?,
    @SerializedName("bank")
    @Expose
    var bank: BankDto?,
)