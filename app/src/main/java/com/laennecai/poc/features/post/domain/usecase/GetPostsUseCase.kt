package com.laennecai.poc.features.post.domain.usecase

import com.laennecai.poc.features.post.data.model.Post
import com.laennecai.poc.features.post.domain.repository.PostRepository

class GetPostsUseCase(private val postRepository: PostRepository) {
    // Updated to return List<Post> directly or throw an exception
    suspend operator fun invoke(): List<Post> {
        // Business logic: For example, filter posts or combine with other data
        // For now, we'll just pass through, but we can add logic like processing titles here
        val posts = postRepository.getPosts() // Assuming postRepository.getPosts() now returns List<Post> or throws
        // Example processing: could throw custom exception if needed
        return posts.map { it.copy(title = "Processed: ${it.title}") }
    }
} 