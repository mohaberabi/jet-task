package com.example.todo.core.util

import androidx.compose.runtime.Composable


@Composable
fun Loader(
    loading: Boolean,
    empty: Boolean,
    placeHolder: @Composable () -> Unit,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit,
) {

    
    if (empty) {
        placeHolder()
    } else {
        content()
    }
}