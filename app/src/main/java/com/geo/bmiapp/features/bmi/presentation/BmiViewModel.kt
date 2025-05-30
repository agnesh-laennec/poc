package com.geo.bmiapp.features.bmi.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.geo.bmiapp.features.bmi.domain.usecase.BmiInput
import com.geo.bmiapp.features.bmi.domain.usecase.BmiOutput
import com.geo.bmiapp.features.bmi.domain.usecase.CalculateBmiUseCase

class BmiViewModel(private val calculateBmiUseCase: CalculateBmiUseCase = CalculateBmiUseCase()) : ViewModel() {

    var heightText by mutableStateOf("")
    var weightText by mutableStateOf("")
    var bmiResult by mutableStateOf<BmiOutput?>(null)
    var showError by mutableStateOf(false)
        private set

    fun calculateBmi() {
        val heightCm = heightText.toDoubleOrNull()
        val weightKg = weightText.toDoubleOrNull()

        if (heightCm != null && weightKg != null) {
            val result = calculateBmiUseCase(BmiInput(heightCm = heightCm, weightKg = weightKg))
            if (result != null) {
                bmiResult = result
                showError = false
            } else {
                bmiResult = null
                showError = true
            }
        } else {
            bmiResult = null
            showError = true
        }
    }

    fun onHeightChange(newHeight: String) {
        heightText = newHeight
        showError = false
        bmiResult = null // Clear previous result on input change
    }

    fun onWeightChange(newWeight: String) {
        weightText = newWeight
        showError = false
        bmiResult = null // Clear previous result on input change
    }
} 