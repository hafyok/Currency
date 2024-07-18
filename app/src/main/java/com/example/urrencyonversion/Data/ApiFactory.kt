package com.example.urrencyonversion.Data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiFactory {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cbr-xml-daily.ru/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val currencyApi: CurrencyApi = retrofit.create(CurrencyApi::class.java)


}