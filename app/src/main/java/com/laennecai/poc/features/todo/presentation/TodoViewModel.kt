package com.laennecai.poc.features.todo.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.laennecai.poc.features.todo.data.model.Todo

class TodoViewModel : ViewModel() {
    var todos by mutableStateOf<List<Todo>>(emptyList())
        private set

    var newTodoTitle by mutableStateOf("")
        private set

    var newTodoDescription by mutableStateOf("")
        private set

    fun addTodo() {
        if (newTodoTitle.isNotBlank()) {
            val newTodo = Todo(
                id = todos.size + 1,
                title = newTodoTitle,
                description = newTodoDescription
            )
            todos = todos + newTodo
            clearNewTodoInputs()
        }
    }

    fun toggleTodoCompletion(todoId: Int) {
        todos = todos.map { todo ->
            if (todo.id == todoId) {
                todo.copy(isCompleted = !todo.isCompleted)
            } else {
                todo
            }
        }
    }

    fun deleteTodo(todoId: Int) {
        todos = todos.filter { it.id != todoId }
    }

    fun updateNewTodoTitle(title: String) {
        newTodoTitle = title
    }

    fun updateNewTodoDescription(description: String) {
        newTodoDescription = description
    }

    private fun clearNewTodoInputs() {
        newTodoTitle = ""
        newTodoDescription = ""
    }
} 