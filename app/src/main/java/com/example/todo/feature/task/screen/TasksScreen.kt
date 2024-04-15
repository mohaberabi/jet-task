package com.example.todo.feature.task.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todo.R
import com.example.todo.core.composable.AppBar
import com.example.todo.feature.task.composable.TaskBody
import com.example.todo.feature.task.data.model.Task
import com.example.todo.feature.task.viewmodel.TaskViewModel


@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    onAddTask: () -> Unit = {},
    onTaskClick: (Task) -> Unit = {},
    openDrawer: () -> Unit = {},
    viewModel: TaskViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsState()


    Scaffold(
        topBar = {
            AppBar(
                title = "", navIcon = Icons.Default.Menu,
                onNavIconClick = {
                    openDrawer()
                },
            )
        },
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.add_task))
            }
        }
    ) {

            padd ->

        TaskBody(
            loading = state.isLoading,
            tasks = state.items,
            currentFilteringLabel = state.filterInfo.currentFilteringLabel,
            noTasksLabel = state.filterInfo.noTasksLabel,
            noTasksIconRes = state.filterInfo.noTaskIconRes,
            onRefresh = {},
            onTaskClick = onTaskClick,
            onTaskCheckedChange = { task, checkd ->
                viewModel.completeTask(task, checkd)
            },
            modifier = Modifier.padding(padd)
        )
    }
}