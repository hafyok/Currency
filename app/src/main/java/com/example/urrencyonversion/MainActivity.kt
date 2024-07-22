package com.example.urrencyonversion

import android.os.Bundle
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
import com.example.urrencyonversion.Presentation.CurrencyScreen
import com.example.urrencyonversion.Presentation.CurrencyViewModel
import com.example.urrencyonversion.ui.theme.СurrencyСonversionTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val currencyViewModel = CurrencyViewModel()
        setContent {
            СurrencyСonversionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CurrencyScreen(currencyViewModel, Modifier.padding(innerPadding))
                }
            }
        }
    }
}
