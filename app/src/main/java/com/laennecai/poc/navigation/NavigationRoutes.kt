package com.laennecai.poc.navigation

// Nested graph routes
object NestedRoutes {
    const val POST_LIST = "postList"
    const val POST_DETAIL = "postDetail/{postId}"
    fun postDetail(postId: Int) = "postDetail/$postId"

    const val BMI_CALCULATOR = "bmiCalculator"
    const val TODO_LIST = "todoList"
} 