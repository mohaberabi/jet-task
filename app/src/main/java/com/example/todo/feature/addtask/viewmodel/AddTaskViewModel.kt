package com.example.todo.feature.addtask.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.R
import com.example.todo.feature.task.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,

    ) : ViewModel() {
    private val _state = MutableStateFlow(AddTaskState())
    val state = _state

    fun titleChanged(ttl: String) {
        _state.update {
            it.copy(
                title = ttl
            )
        }
    }

    fun onSnackBarSeen() {
        _state.update {
            it.copy(userMessage = null)
        }

    }

    fun loadTask(id: String) {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            val task = taskRepository.getTask(id)
            if (task != null) {
                _state.update {
                    it.copy(
                        taskId = task.id,
                        title = task.title,
                        description = task.description,
                    )
                }
            }
            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun createNewTask() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        taskRepository.createTask(_state.value.title, _state.value.description)
        _state.update {
            it.copy(
                isSaved = true,
                isLoading = false,
                userMessage = R.string.successfully_added_task_message
            )
        }
    }


    fun descriptionChanged(desc: String) {
        _state.update {
            it.copy(
                description = desc
            )
        }
    }


    private fun updateTask() {

        _state.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            taskRepository.updateTask(
                _state.value.taskId!!,
                _state.value.title,
                _state.value.description
            )
            _state.update {
                it.copy(
                    isSaved = true,
                    isLoading = false,
                    userMessage = R.string.successfully_added_task_message
                )
            }
        }
    }


    fun saveTask() {
        if (!_state.value.isFormValid) {
            _state.update {
                it.copy(
                    userMessage = R.string.empty_task_message
                )
            }
            return
        }

        if (_state.value.taskId == null) {
            createNewTask()
        } else {
            updateTask()
        }
    }


}