package com.example.todo.core.util

sealed class Async<out T> {

    data object Loading : Async<Nothing>()
    data class Error(val message: String) : Async<Nothing>()
    data class Done<out T>(val data: T) : Async<T>()
}