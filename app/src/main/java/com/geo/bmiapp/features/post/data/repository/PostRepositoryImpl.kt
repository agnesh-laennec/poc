package com.geo.bmiapp.features.post.data.repository

import com.geo.bmiapp.features.post.data.model.Post
import com.geo.bmiapp.features.post.domain.repository.ApiService
import com.geo.bmiapp.features.post.domain.repository.PostRepository

class PostRepositoryImpl(private val apiService: ApiService) : PostRepository {
    override suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }
    // override suspend fun getPostById(id: Int): Result<Post> { ... } // For later
} 