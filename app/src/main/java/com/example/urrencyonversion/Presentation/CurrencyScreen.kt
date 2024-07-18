package com.example.urrencyonversion.Presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(viewModel: CurrencyViewModel = CurrencyViewModel(), modifier: Modifier = Modifier) {
    val rates by viewModel.currencyRates.collectAsState()
    val error by viewModel.error.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchRates()
    }
    
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(32.dp)
    ){
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {expanded = !expanded}) {
            if (error != null) {
                TextField(value = "Error: $error",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor()
                )
            } else {
                TextField(value = rates.entries.toString(),
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    rates.entries.toList().forEach{ item ->
                        DropdownMenuItem(text = { Text("${item.key}: ${item.value.value}") }, onClick = { /*TODO*/ })
                    }
                }
                /*LazyColumn {
                    items(rates.entries.toList()) { entry ->
                        Text(text = "${entry.key}: ${entry.value.value}")
                    }
                }*/
            }
        }
    }
    

    /*Column(modifier = Modifier.padding(16.dp)) {
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
    }*/
}