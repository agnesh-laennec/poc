package com.laennecai.poc.features.post.data.model

data class ApiResponse<T>(
    val data: T? = null,
    val error: String? = null
) 