package com.laennecai.poc.features.post.domain.repository

import com.laennecai.poc.features.post.data.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
} 