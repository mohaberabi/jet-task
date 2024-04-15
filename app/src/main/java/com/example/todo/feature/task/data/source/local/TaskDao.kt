package com.example.todo.feature.task.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {


    @Query("SELECT * FROM tasks")
    fun observeAll(): Flow<List<LocalTask>>

    @Query("SELECT * FROM TASKS WHERE id=:id")

    fun observeById(id: String): Flow<LocalTask>

    @Query("SELECT * FROM tasks")

    suspend fun getAll(): List<LocalTask>


    @Query("SELECT * FROM TASKS WHERE id=:id")

    suspend fun getById(id: String): LocalTask


    //update insert "suitable for offlien first apps "
    @Upsert
    suspend fun upsert(task: LocalTask)

    @Upsert
    suspend fun upsertAll(tasks: List<LocalTask>)

    @Query("UPDATE tasks SET isCompleted = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: String, completed: Boolean)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteById(taskId: String): Int


    @Query("DELETE FROM tasks")
    suspend fun deleteAll()


    @Query("DELETE FROM tasks WHERE isCompleted = 1")
    suspend fun deleteCompleted(): Int
}