package com.laennecai.poc.features.post.data.repository

import com.laennecai.poc.features.post.data.model.Post
import com.laennecai.poc.features.post.domain.repository.ApiService
import com.laennecai.poc.features.post.domain.repository.PostRepository
import com.laennecai.poc.features.post.domain.Result // Updated import

class PostRepositoryImpl(private val apiService: ApiService) : PostRepository {
    override suspend fun getPosts(): Result<List<Post>> {
        return try {
            val posts = apiService.getPosts()
            Result.Success(posts)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    // override suspend fun getPostById(id: Int): Result<Post> { ... } // For later
} 