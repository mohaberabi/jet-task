package com.example.todo.feature.task.data.source.remote

interface TaskRemoteDatasource {


    suspend fun loadTasks(): List<RemoteTask>
    suspend fun saveTasks(tasks: List<RemoteTask>)

}