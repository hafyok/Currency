package com.example.urrencyonversion.Data

import com.google.gson.annotations.SerializedName

data class CurrencyResponce(
    @SerializedName("Date") val date: String,
    @SerializedName("PreviousDate") val previousDate: String,
    @SerializedName("PreviousURL") val previousURL: String,
    @SerializedName("Timestamp") val timestamp: String,
    @SerializedName("Valute") val valute: Map<String, CurrencyInfo>
)

data class CurrencyInfo(
    @SerializedName("ID") val id: String,
    @SerializedName("NumCode") val numCode: String,
    @SerializedName("CharCode") val charCode: String,
    @SerializedName("Nominal") val nominal: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Value") val value: Double,
    @SerializedName("Previous") val previous: Double
)