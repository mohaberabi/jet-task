package com.example.todo.feature.task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.R
import com.example.todo.feature.task.data.model.Task
import com.example.todo.feature.task.data.repository.DefaultTaskRepository
import com.example.todo.feature.task.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(


    private val taskRepository: TaskRepository,

    ) : ViewModel() {


    private val _state = MutableStateFlow(TaskState())
    val state = _state


    init {

        viewModelScope.launch {
            taskRepository.getTasksStream().collect { tasks ->
                _state.update {
                    it.copy(items = tasks)
                }
            }
        }
    }

    fun completeTask(task: Task, completed: Boolean) = viewModelScope.launch {


        _state.update {
            it.copy(
                isLoading = true
            )
        }
        if (completed) {
            taskRepository.completeTask(task.id)
            showSnackbarMessage(R.string.task_marked_complete)

        } else {
            taskRepository.activateTask(task.id)
            showSnackbarMessage(R.string.task_marked_active)
        }
        _state.update {
            it.copy(
                isLoading = false
            )
        }
    }

    fun snackbarMessageShown() {
        _state.update {
            it.copy(
                userMessage = null
            )
        }
    }

    private fun showSnackbarMessage(message: Int) {
        _state.update {
            it.copy(
                userMessage = message
            )
        }
    }
}