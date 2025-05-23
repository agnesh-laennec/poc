package com.laennecai.poc.features.todo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todo(
    val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
) : Parcelable

//test