package com.example.todo.feature.task.data.source.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalTask::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao


}