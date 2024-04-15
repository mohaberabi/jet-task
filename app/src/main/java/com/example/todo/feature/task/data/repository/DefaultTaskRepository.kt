package com.example.todo.feature.task.data.repository

import com.example.todo.feature.task.data.dto.toLocal
import com.example.todo.feature.task.data.dto.toRemote
import com.example.todo.feature.task.data.dto.toTask
import com.example.todo.feature.task.data.model.Task
import com.example.todo.feature.task.data.source.local.TaskDao
import com.example.todo.feature.task.data.source.remote.TaskRemoteDatasource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class DefaultTaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val taskRemoteDatasource: TaskRemoteDatasource,
) : TaskRepository {
    override fun getTasksStream(): Flow<List<Task>> {
        return taskDao.observeAll().map { tasks ->
            withContext(Dispatchers.IO) {
                tasks.map {
                    it.toTask()
                }
            }
        }
    }

    override suspend fun getTasks(forceUpdate: Boolean): List<Task> {
        if (forceUpdate) {
            refresh()
        }
        return withContext(Dispatchers.IO) {
            taskDao.getAll().map {
                it.toTask()
            }
        }
    }

    override suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val remoteTasks = taskRemoteDatasource.loadTasks()
            taskDao.deleteAll()
            taskDao.upsertAll(remoteTasks.map {
                it.toLocal()
            })
        }
    }

    override fun getTaskStream(taskId: String): Flow<Task?> {
        return taskDao.observeById(taskId).map {
            it.toTask()
        }

    }

    override suspend fun getTask(
        taskId: String,
        forceUpdate: Boolean
    ): Task {
        if (forceUpdate) {
            refresh()
        }
        return taskDao.getById(taskId).toTask()
    }

    override suspend fun refreshTask(taskId: String) {
        refresh()
    }

    override suspend fun createTask(
        title: String,
        description: String
    ): String {

        val taskId = withContext(Dispatchers.IO) {
            UUID.randomUUID().toString()
        }
        val task = Task(
            title = title,
            description = description,
            id = taskId,
        )
        taskDao.upsert(task.toLocal())
        return taskId
    }

    override suspend fun updateTask(
        taskId: String,
        title: String,
        description: String
    ) {
        val task = getTask(taskId).copy(
            title = title,
            description = description
        )

        taskDao.upsert(task.toLocal())
        saveTasksToNetwork()
    }

    override suspend fun completeTask(taskId: String) {
        taskDao.updateCompleted(taskId = taskId, completed = true)
        saveTasksToNetwork()
    }

    override suspend fun activateTask(taskId: String) {
        taskDao.updateCompleted(taskId = taskId, completed = false)
        saveTasksToNetwork()
    }

    override suspend fun clearCompletedTasks() {
        taskDao.deleteCompleted()
        saveTasksToNetwork()
    }

    override suspend fun deleteAllTasks() {
        taskDao.deleteAll()
        saveTasksToNetwork()
    }

    override suspend fun deleteTask(taskId: String) {

        taskDao.deleteById(taskId)
        saveTasksToNetwork()
    }

    private fun saveTasksToNetwork() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val localTasks = taskDao.getAll()
                val networkTasks = withContext(Dispatchers.IO) {
                    localTasks.map {
                        it.toTask()
                    }
                }
                taskRemoteDatasource.saveTasks(networkTasks.map {
                    it.toRemote()
                })
            } catch (e: Exception) {

            }
        }

    }
}