package com.example.todo.feature.task.viewmodel

import com.example.todo.R
import com.example.todo.feature.task.data.model.Task

data class TaskState(
    val items: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val filterInfo: FilteringUiInfo = FilteringUiInfo(),
    val userMessage: Int? = null,


    )


data class FilteringUiInfo(
    val currentFilteringLabel: Int = R.string.label_all,
    val noTasksLabel: Int = R.string.no_tasks_all,
    val noTaskIconRes: Int = R.drawable.logo_no_fill,
)