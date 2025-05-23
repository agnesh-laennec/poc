package com.laennecai.poc.features.bmi.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CalculateBmiUseCaseTest {
    private val calculateBmiUseCase = CalculateBmiUseCase()

    @Test
    fun `calculate BMI with valid inputs returns correct result`() {
        // Given
        val heightCm = 170.0
        val weightKg = 70.0
        val input = BmiInput(heightCm, weightKg)

        // When
        val result = calculateBmiUseCase(input)

        // Then
        assert(result != null)
        result?.bmi?.let { assertEquals(24.22, it, 0.01) }
        assertEquals("Normal weight", result?.category)
    }

    @Test
    fun `calculate BMI with zero height returns null`() {
        // Given
        val input = BmiInput(heightCm = 0.0, weightKg = 70.0)

        // When
        val result = calculateBmiUseCase(input)

        // Then
        assertNull(result)
    }

    @Test
    fun `calculate BMI with zero weight returns null`() {
        // Given
        val input = BmiInput(heightCm = 170.0, weightKg = 0.0)

        // When
        val result = calculateBmiUseCase(input)

        // Then
        assertNull(result)
    }

    @Test
    fun `calculate BMI with negative height returns null`() {
        // Given
        val input = BmiInput(heightCm = -170.0, weightKg = 70.0)

        // When
        val result = calculateBmiUseCase(input)

        // Then
        assertNull(result)
    }

    @Test
    fun `calculate BMI with negative weight returns null`() {
        // Given
        val input = BmiInput(heightCm = 170.0, weightKg = -70.0)

        // When
        val result = calculateBmiUseCase(input)

        // Then
        assertNull(result)
    }

    @Test
    fun `calculate BMI categories correctly`() {
        // Test cases for different BMI categories
        val testCases = listOf(
            Triple(160.0, 45.0, "Underweight"),    // BMI = 17.58
            Triple(170.0, 65.0, "Normal weight"),  // BMI = 22.49
            Triple(180.0, 85.0, "Overweight"),     // BMI = 26.23
            Triple(170.0, 90.0, "Obese")           // BMI = 31.14
        )

        testCases.forEach { (height, weight, expectedCategory) ->
            // Given
            val input = BmiInput(heightCm = height, weightKg = weight)

            // When
            val result = calculateBmiUseCase(input)

            // Then
            assert(result != null)
            assertEquals(expectedCategory, result?.category)
        }
    }
} 