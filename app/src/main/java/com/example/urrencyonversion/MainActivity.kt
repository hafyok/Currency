package com.example.urrencyonversion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.urrencyonversion.Data.CurrencyApi
import com.example.urrencyonversion.Data.CurrencyResponce
import com.example.urrencyonversion.ui.theme.СurrencyСonversionTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.cbr-xml-daily.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val currencyApi: CurrencyApi = retrofit.create(CurrencyApi::class.java)

        currencyApi.getCurrencies().enqueue(object : Callback<CurrencyResponce> {
            override fun onResponse(
                call: Call<CurrencyResponce>,
                response: Response<CurrencyResponce>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        currency->
                        Log.d("Currencies", currency.valute.toString())
                    }?: run{
                        Log.d("Currencies", "No data")
                    }
                }
                else{
                    Log.d("Currencies", "Failed to get currency. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CurrencyResponce>, t: Throwable) {
                Log.d("Currencies", "Network error: ${t.message}")
            }
        })
        setContent {
            СurrencyСonversionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    СurrencyСonversionTheme {
        Greeting("Android")
    }
}