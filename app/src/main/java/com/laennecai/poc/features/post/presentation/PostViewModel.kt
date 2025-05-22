package com.laennecai.poc.features.post.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laennecai.poc.features.post.data.model.Post
import com.laennecai.poc.features.post.data.remote.ApiClient
import com.laennecai.poc.features.post.data.repository.PostRepositoryImpl
import com.laennecai.poc.features.post.domain.usecase.GetPostsUseCase
import com.laennecai.poc.features.post.domain.Result // Will be created next
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    var postsResult by mutableStateOf<Result<List<Post>>>(Result.Loading)
        private set

    private val postRepository = PostRepositoryImpl(ApiClient.instance)
    private val getPostsUseCase = GetPostsUseCase(postRepository)

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            postsResult = Result.Loading
            postsResult = getPostsUseCase()
        }
    }
} 