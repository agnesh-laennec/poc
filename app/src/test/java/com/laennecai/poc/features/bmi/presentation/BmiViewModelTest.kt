package com.laennecai.poc.features.bmi.presentation

import com.laennecai.poc.features.bmi.domain.usecase.BmiInput
import com.laennecai.poc.features.bmi.domain.usecase.BmiOutput
import com.laennecai.poc.features.bmi.domain.usecase.FakeCalculateBmiUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BmiViewModelTest {
    private lateinit var calculateBmiUseCase: FakeCalculateBmiUseCase
    private lateinit var viewModel: BmiViewModel

    @Before
    fun setup() {
        calculateBmiUseCase = FakeCalculateBmiUseCase()
        viewModel = BmiViewModel(calculateBmiUseCase)
    }

    @Test
    fun `onHeightChange updates height and clears result`() {
        // Given
        val newHeight = "170"
        viewModel.bmiResult = BmiOutput(24.22, "Normal weight")

        // When
        viewModel.onHeightChange(newHeight)

        // Then
        assertEquals(newHeight, viewModel.heightText)
        assertNull(viewModel.bmiResult)
        assertFalse(viewModel.showError)
    }

    @Test
    fun `onWeightChange updates weight and clears result`() {
        // Given
        val newWeight = "70"
        viewModel.bmiResult = BmiOutput(24.22, "Normal weight")

        // When
        viewModel.onWeightChange(newWeight)

        // Then
        assertEquals(newWeight, viewModel.weightText)
        assertNull(viewModel.bmiResult)
        assertFalse(viewModel.showError)
    }

    @Test
    fun `calculateBmi with valid inputs shows result`() {
        // Given
        viewModel.heightText = "170"
        viewModel.weightText = "70"
        val expectedResult = BmiOutput(24.22, "Normal weight")
        calculateBmiUseCase.setResult(expectedResult)

        // When
        viewModel.calculateBmi()

        // Then
        assertEquals(expectedResult, viewModel.bmiResult)
        assertFalse(viewModel.showError)
        assertEquals(BmiInput(170.0, 70.0), calculateBmiUseCase.getLastInput())
    }

    @Test
    fun `calculateBmi with invalid height shows error`() {
        // Given
        viewModel.heightText = "invalid"
        viewModel.weightText = "70"

        // When
        viewModel.calculateBmi()

        // Then
        assertNull(viewModel.bmiResult)
        assertTrue(viewModel.showError)
    }

    @Test
    fun `calculateBmi with invalid weight shows error`() {
        // Given
        viewModel.heightText = "170"
        viewModel.weightText = "invalid"

        // When
        viewModel.calculateBmi()

        // Then
        assertNull(viewModel.bmiResult)
        assertTrue(viewModel.showError)
    }

    @Test
    fun `calculateBmi with null result from useCase shows error`() {
        // Given
        viewModel.heightText = "170"
        viewModel.weightText = "70"
        calculateBmiUseCase.setResult(null)

        // When
        viewModel.calculateBmi()

        // Then
        assertNull(viewModel.bmiResult)
        assertTrue(viewModel.showError)
    }
} 