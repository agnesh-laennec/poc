package com.laennecai.poc.features.post.data.repository

import com.laennecai.poc.features.post.data.model.Post
import com.laennecai.poc.features.post.domain.repository.ApiService
import com.laennecai.poc.features.post.domain.repository.PostRepository

class PostRepositoryImpl(private val apiService: ApiService) : PostRepository {
    override suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }
    // override suspend fun getPostById(id: Int): Result<Post> { ... } // For later
} 