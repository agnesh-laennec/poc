package com.laennecai.poc.features.post.data.repository

import com.laennecai.poc.features.post.data.model.Post
import com.laennecai.poc.features.post.data.remote.ApiClient
import com.laennecai.poc.features.post.domain.repository.PostRepository
import com.laennecai.poc.features.post.domain.repository.ApiService

class PostRepositoryImpl(private val apiService: ApiService) : PostRepository {
    override suspend fun getPosts(): List<Post> {
        val response = apiService.getPosts()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        }
        throw Exception("Failed to fetch posts: ${response.code()}")
    }
    // override suspend fun getPostById(id: Int): Result<Post> { ... } // For later
} 