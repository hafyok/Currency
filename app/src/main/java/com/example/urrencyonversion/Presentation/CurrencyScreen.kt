package com.example.urrencyonversion.Presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyScreen(viewModel: CurrencyViewModel = CurrencyViewModel(), modifier: Modifier = Modifier) {
    val rates by viewModel.currencyRates.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRates()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Currency Converter", style = MaterialTheme.typography.labelLarge)

        Spacer(modifier = Modifier.height(16.dp))

        if (error != null) {
            Text(text = "Error: $error")
        } else {
            LazyColumn {
                items(rates.entries.toList()) { entry ->
                    Text(text = "${entry.key}: ${entry.value.value}")
                }
            }
        }
    }
}