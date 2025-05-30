package com.geo.bmiapp.features.bmi.domain.usecase

import java.math.BigDecimal
import java.math.RoundingMode

data class BmiInput(val heightCm: Double, val weightKg: Double)
data class BmiOutput(val bmi: Double, val category: String)

open class CalculateBmiUseCase {
    open operator fun invoke(input: BmiInput): BmiOutput? {
        if (input.heightCm <= 0 || input.weightKg <= 0) {
            return null
        }
        val heightM = input.heightCm / 100.0
        val bmi = input.weightKg / (heightM * heightM)
        val roundedBmi = BigDecimal(bmi).setScale(2, RoundingMode.HALF_EVEN).toDouble()
        val category = getBmiCategory(roundedBmi)
        return BmiOutput(roundedBmi, category)
    }

    private fun getBmiCategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 24.9 -> "Normal weight"
            bmi < 29.9 -> "Overweight"
            else -> "Obese"
        }
    }
} 