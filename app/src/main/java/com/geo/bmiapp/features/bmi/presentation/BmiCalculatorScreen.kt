package com.geo.bmiapp.features.bmi.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.geo.bmiapp.ui.theme.PocTheme

@Composable
fun BmiCalculatorScreen(modifier: Modifier = Modifier, bmiViewModel: BmiViewModel = viewModel()) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "BMI Calculator",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = bmiViewModel.heightText,
            onValueChange = { bmiViewModel.onHeightChange(it) },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = bmiViewModel.showError && bmiViewModel.heightText.toDoubleOrNull() == null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bmiViewModel.weightText,
            onValueChange = { bmiViewModel.onWeightChange(it) },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = bmiViewModel.showError && bmiViewModel.weightText.toDoubleOrNull() == null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { bmiViewModel.calculateBmi() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate BMI")
        }

        if (bmiViewModel.showError) {
            Text(
                text = "Please enter valid height and weight.",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        bmiViewModel.bmiResult?.let { (bmiValue, category) ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Your BMI: $bmiValue",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Category: $category",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BmiCalculatorScreenPreview() {
    PocTheme {
        BmiCalculatorScreen()
    }
} 