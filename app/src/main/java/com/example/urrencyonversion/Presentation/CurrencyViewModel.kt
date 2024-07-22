package com.example.urrencyonversion.Presentation

import android.util.Log
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

    private val _amount = MutableStateFlow("")
    //val amountFirst: StateFlow<String> = _amountFirst.asStateFlow()

    private val _currentFirstCurrency = MutableStateFlow("RUB (Российский рубль)")
    //val currentFirstCurrency: StateFlow<String> = _currentFirstCurrency.asStateFlow()

    private val _currentSecondCurrency = MutableStateFlow("RUB (Российский рубль)")
    val currentSecondCurrency: StateFlow<String> = _currentSecondCurrency.asStateFlow()

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()

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

    fun updateFirstCurrency(newCurrency: String){
        _currentFirstCurrency.value = newCurrency
        Log.d("Currencies", currencyRates.value[_currentFirstCurrency.value].toString())
    }

    fun updateSecondCurrency(newCurrency: String){
        _currentSecondCurrency.value = newCurrency
        Log.d("Currencies", currencyRates.value[_currentSecondCurrency.value].toString())
    }

    fun updateFirstAmount(newAmount: String){
        _amount.value = newAmount
    }

    fun calculation(){
        val rateFirst = _currencyRates.value[_currentFirstCurrency.value]?.value ?: 1.0
        val amountValueFirst = _amount.value.toDoubleOrNull() ?: 0.0
        val nominalFirst = _currencyRates.value[_currentFirstCurrency.value]?.nominal ?: 1
        val firstCur = (amountValueFirst * (rateFirst / nominalFirst))

        val rateSecond = _currencyRates.value[_currentSecondCurrency.value]?.value ?: 1.0
        val nominalSecond = _currencyRates.value[_currentSecondCurrency.value]?.nominal ?: 1
        _result.value = (firstCur / (rateSecond / nominalSecond)).toString()

        Log.d("Currencies", _result.value)
    }
}