package com.example.urrencyonversion.Domain

data class Currency(
    val rates: Map<String, Double>,
    val date: String
)
