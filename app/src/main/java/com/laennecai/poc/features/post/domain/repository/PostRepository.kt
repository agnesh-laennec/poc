package com.laennecai.poc.features.post.domain.repository

import com.laennecai.poc.features.post.data.model.Post
// import com.laennecai.poc.features.post.domain.Result // Removed custom Result import

interface PostRepository {
    // Updated to return List<Post> directly or throw an exception
    suspend fun getPosts(): List<Post>
    // suspend fun getPostById(id: Int): Result<Post> // Example for later: if we fetch single post by ID
} 