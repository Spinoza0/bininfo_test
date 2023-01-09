package com.spinoza.bininfotest.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "bins")
data class Bin(
    @PrimaryKey
    @SerializedName("value")
    val value: String,
) : java.io.Serializable