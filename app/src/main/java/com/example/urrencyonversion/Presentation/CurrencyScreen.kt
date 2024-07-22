package com.example.urrencyonversion.Presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.urrencyonversion.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(
    viewModel: CurrencyViewModel = CurrencyViewModel(),
    modifier: Modifier = Modifier
) {
    val rates by viewModel.currencyRates.collectAsState()
    val error by viewModel.error.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var amount by rememberSaveable { mutableStateOf("") }
    var currentCurrency by rememberSaveable { mutableStateOf("RUB (Российский рубль)") }

    LaunchedEffect(Unit) {
        viewModel.fetchRates()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Центрирование содержимого внутри Box
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_currency_exchange_24), // Замените на вашу иконку
            contentDescription = "Centered Icon"
        )
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Отступ между элементами
        ) {
            //TODO() Добавить запятые и не целые значения в форму
            OutlinedTextField(
                value = amount,
                onValueChange = { newText -> if (newText.all { it.isDigit() }) amount = newText },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                //readOnly = true,
                label = {
                    if (error != null) Text("Error: $error")
                    else Text("RUB (Российский рубль)")
                },
                modifier = Modifier.weight(1f)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = currentCurrency,
                    label = { Text(text = "Валюта") },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    rates.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text("${item.key} (${item.value.name})")
                            },
                            onClick = {
                                currentCurrency = "${item.key} (${item.value.name})"
                                expanded = false
                                viewModel.updateCurrency(item.key)
                            }
                        )
                    }
                }
            }
        }
        Button(onClick = {
            viewModel.updateAmount(amount)
            viewModel.calculation()
        }) {
            Text(text = "Convert")
        }
    }
}