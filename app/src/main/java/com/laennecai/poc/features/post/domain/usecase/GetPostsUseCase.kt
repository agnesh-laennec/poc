package com.laennecai.poc.features.post.domain.usecase

import com.laennecai.poc.features.post.data.model.Post
import com.laennecai.poc.features.post.domain.repository.PostRepository
import com.laennecai.poc.features.post.domain.Result // Updated import

class GetPostsUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(): Result<List<Post>> {
        // Business logic: For example, filter posts or combine with other data
        // For now, we'll just pass through, but we can add logic like processing titles here
        val result = postRepository.getPosts()
        if (result is Result.Success) {
            return Result.Success(result.data.map { it.copy(title = "Processed: ${it.title}") })
        }
        return result
    }
} 