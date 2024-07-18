package com.example.urrencyonversion.Presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urrencyonversion.Data.ApiFactory.currencyApi
import com.example.urrencyonversion.Data.CurrencyInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyViewModel: ViewModel() {
    private val _currencyRates = MutableStateFlow<Map<String, CurrencyInfo>>(emptyMap())
    val currencyRates: StateFlow<Map<String, CurrencyInfo>> = _currencyRates.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchRates() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    currencyApi.getCurrencies().execute()
                }
                if (response.isSuccessful) {
                    _currencyRates.value = response.body()?.valute ?: emptyMap()
                } else {
                    _error.value = response.message()
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}