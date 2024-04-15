package com.example.todo.feature.task.composable

import TaskItem
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.todo.R
import com.example.todo.core.util.Loader
import com.example.todo.feature.task.data.model.Task


@Composable
fun TaskBody(
    loading: Boolean,
    tasks: List<Task>,
    @StringRes currentFilteringLabel: Int,
    @StringRes noTasksLabel: Int,
    @DrawableRes noTasksIconRes: Int,
    onRefresh: () -> Unit,
    onTaskClick: (Task) -> Unit,
    onTaskCheckedChange: (Task, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Loader(
        loading = loading,
        empty = tasks.isEmpty(),
        placeHolder = {
            Text(text = "No Tasks")
        },
        onRefresh = {

        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin))
        ) {
            Text(
                text = stringResource(currentFilteringLabel),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.list_item_padding),
                    vertical = dimensionResource(id = R.dimen.vertical_margin)
                ),
                style = MaterialTheme.typography.headlineMedium
            )
            LazyColumn {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onTaskClick = onTaskClick,
                        onCheckedChange = { onTaskCheckedChange(task, it) }
                    )
                }
            }
        }
    }
}