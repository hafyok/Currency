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
    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var amount by rememberSaveable { mutableStateOf("") }
    var currentFirstCurrency by rememberSaveable { mutableStateOf("RUB (Российский рубль)") }
    var currentSecondCurrency by rememberSaveable { mutableStateOf("RUB (Российский рубль)") }

    LaunchedEffect(Unit) {
        viewModel.fetchRates()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_currency_exchange_24),
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
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded1,
                onExpandedChange = { expanded1 = !expanded1 },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = currentFirstCurrency,
                    label = { Text(text = "Валюта 1") },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded1)
                    },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(expanded = expanded1, onDismissRequest = { expanded1 = false }) {
                    rates.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text("${item.key} (${item.value.name})")
                            },
                            onClick = {
                                currentFirstCurrency = "${item.key} (${item.value.name})"
                                viewModel.updateFirstCurrency(item.key)
                                expanded1 = false
                            }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = expanded2,
                onExpandedChange = { expanded2 = !expanded2 },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = currentSecondCurrency,
                    label = { Text(text = "Валюта 2") },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded2)
                    },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(expanded = expanded2, onDismissRequest = { expanded2 = false }) {
                    rates.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text("${item.key} (${item.value.name})")
                            },
                            onClick = {
                                currentSecondCurrency = "${item.key} (${item.value.name})"
                                viewModel.updateSecondCurrency(item.key)
                                expanded2 = false
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //TODO() Добавить запятые и не целые значения в форму
            OutlinedTextField(
                value = amount,
                onValueChange = { newText -> if (newText.all { it.isDigit() }) amount = newText },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Сумма") },
                modifier = Modifier.weight(1f)
            )
        }
        Button(onClick = {
            viewModel.updateFirstAmount(amount)
            viewModel.calculation()
        }) {
            Text(text = "Convert")
        }
    }
}