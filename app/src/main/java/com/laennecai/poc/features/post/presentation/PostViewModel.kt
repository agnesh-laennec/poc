package com.laennecai.poc.features.post.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laennecai.poc.features.post.data.model.Post
import com.laennecai.poc.features.post.data.repository.PostRepositoryImpl
import com.laennecai.poc.features.post.data.remote.ApiClient
import com.laennecai.poc.features.post.domain.usecase.GetPostsUseCase
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    var postsResult: Result<List<Post>>? by mutableStateOf(null)
        private set

    private val postRepository = PostRepositoryImpl(ApiClient.instance)
    private val getPostsUseCase = GetPostsUseCase(postRepository)

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            try {
                val posts = getPostsUseCase()
                postsResult = Result.success(posts)
            } catch (e: Exception) {
                postsResult = Result.failure(e)
            }
        }
    }
} 