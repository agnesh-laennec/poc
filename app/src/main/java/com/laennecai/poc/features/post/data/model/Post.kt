package com.laennecai.poc.features.post.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
) : Parcelable 