package com.example.todo.feature.task.data.dto

import com.example.todo.feature.task.data.model.Task
import com.example.todo.feature.task.data.source.local.LocalTask
import com.example.todo.feature.task.data.source.remote.RemoteTask
import com.example.todo.feature.task.data.source.remote.TaskStatus


fun Task.toLocal(): LocalTask = LocalTask(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
)

fun Task.toRemote(): RemoteTask = RemoteTask(
    id = id,
    title = title,
    shortDescription = description,
    status = if (isCompleted) {
        TaskStatus.COMPLETE
    } else {
        TaskStatus.ACTIVE
    }
)

fun LocalTask.toRemote(): RemoteTask = RemoteTask(
    id = id,
    title = title,
    shortDescription = description,
    priority = 0,
)

fun LocalTask.toTask(): Task = Task(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
)

fun RemoteTask.toTask(): Task = Task(
    id = id,
    title = title,
    description = shortDescription,
    isCompleted = status == TaskStatus.COMPLETE,
)

fun RemoteTask.toLocal(): LocalTask = LocalTask(
    id = id,
    title = title,
    description = shortDescription,
    isCompleted = status == TaskStatus.COMPLETE,
)

