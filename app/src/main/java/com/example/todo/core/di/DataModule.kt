package com.example.todo.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.todo.feature.task.data.repository.DefaultTaskRepository
import com.example.todo.feature.task.data.repository.TaskRepository
import com.example.todo.feature.task.data.source.local.TaskDao
import com.example.todo.feature.task.data.source.local.TaskDatabase
import com.example.todo.feature.task.data.source.remote.ApiTaskRemoteSource
import com.example.todo.feature.task.data.source.remote.TaskRemoteDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app, TaskDatabase::class.java,
            "tasks.db"
        ).build()
    }

//    @Singleton
//    @Provides
//    fun provideTaskDao(database: TaskDatabase): TaskDao = database.taskDao

    @Singleton
    @Provides
    fun provideRemoteDataSource(): TaskRemoteDatasource {
        return ApiTaskRemoteSource()
    }

    @Singleton
    @Provides
    fun provideTaskRepository(
        database: TaskDatabase,
        taskRemoteDatasource: TaskRemoteDatasource
    ): TaskRepository {
        return DefaultTaskRepository(database.taskDao, taskRemoteDatasource)
    }
}