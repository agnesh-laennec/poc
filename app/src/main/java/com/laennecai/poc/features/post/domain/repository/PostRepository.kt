package com.laennecai.poc.features.post.domain.repository

import com.laennecai.poc.features.post.data.model.Post
import com.laennecai.poc.features.post.domain.Result // Updated import

interface PostRepository {
    suspend fun getPosts(): Result<List<Post>>
    // suspend fun getPostById(id: Int): Result<Post> // Example for later: if we fetch single post by ID
} 