package com.example.todo.feature.addtask.viewmodel

data class AddTaskState(
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val isSaved: Boolean = false,
    val taskId: String? = null,

    ) {

    val isFormValid: Boolean =
        title.isNotEmpty() && description.isNotEmpty()
}
