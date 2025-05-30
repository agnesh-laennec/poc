package com.geo.bmiapp.features.bmi.domain.usecase

class FakeCalculateBmiUseCase : CalculateBmiUseCase() {
    private var result: BmiOutput? = null
    private var lastInput: BmiInput? = null

    fun setResult(result: BmiOutput?) {
        this.result = result
    }

    fun getLastInput(): BmiInput? = lastInput

    override operator fun invoke(input: BmiInput): BmiOutput? {
        lastInput = input
        return result
    }
} 