package com.example.todo.feature.task.data.source.remote

import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class ApiTaskRemoteSource @Inject constructor() : TaskRemoteDatasource {

    // A mutex is used to ensure that reads and writes are thread-safe.
    // build in top of semaphors
    // just to avoid the race condiitions in the operations systems
    //mohab erabi l fkaer nafso fahem hahahaahah
    private val accessMutex = Mutex()
    private var tasks = listOf(
        RemoteTask(
            id = "PISA",
            title = "Build tower in Pisa",
            shortDescription = "Ground looks good, no foundation work required."
        ),
        RemoteTask(
            id = "TACOMA",
            title = "Finish bridge in Tacoma",
            shortDescription = "Found awesome girders at half the cost!"
        )
    )

    override suspend fun loadTasks(): List<RemoteTask> = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return tasks
    }

    override suspend fun saveTasks(newTasks: List<RemoteTask>) = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        tasks = newTasks
    }
}

private const val SERVICE_LATENCY_IN_MILLIS = 2000L
