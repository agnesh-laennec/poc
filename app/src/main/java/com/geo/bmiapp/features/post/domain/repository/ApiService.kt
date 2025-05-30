package com.geo.bmiapp.features.post.domain.repository

import com.geo.bmiapp.features.post.data.model.Post
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
} 