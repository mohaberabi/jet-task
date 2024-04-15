package com.example.todo.feature.addtask.screen

import android.webkit.WebView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todo.feature.addtask.viewmodel.AddTaskViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todo.R
import com.example.todo.core.composable.AppBar
import kotlinx.coroutines.isActive


@Composable
fun AddTaskScreen(
    onTaskUpdate: () -> Unit,
    onBack: () -> Unit,
    viewModel: AddTaskViewModel = hiltViewModel(),
    taskId: String? = null,
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            AppBar(title = "Task Details", onNavIconClick = {
                onBack()
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::saveTask) {
                Icon(Icons.Filled.Done, stringResource(id = R.string.cd_save_task))
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padd ->
        LaunchedEffect(taskId) {
            taskId.let {
                if (it != null) {
                    viewModel.loadTask(it)
                }
            }
        }
        val state by viewModel.state.collectAsState()

        if (state.isLoading) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            AddEditTaskContent(
                loading = state.isLoading,
                modifier = Modifier.padding(padd),
                title = state.title,
                description = state.description,
                onTitleChanged = viewModel::titleChanged,
                onDescriptionChanged = viewModel::descriptionChanged
            )
        }

      
        state.userMessage?.let { userMessage ->
            val snackbarText = stringResource(userMessage)
            LaunchedEffect(snackbarHostState, viewModel, userMessage, snackbarText) {
                snackbarHostState.showSnackbar(snackbarText)
                viewModel.onSnackBarSeen()
                onTaskUpdate()
            }
        }


    }
}

@Composable
private fun AddEditTaskContent(
    loading: Boolean,
    title: String,
    description: String,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    if (loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()

        }
    } else {
        Column(
            modifier
                .fillMaxWidth()
                .padding(all = dimensionResource(id = R.dimen.horizontal_margin))
                .verticalScroll(rememberScrollState())
        ) {
            val textFieldColors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            )
            OutlinedTextField(
                value = title,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onTitleChanged,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.title_hint),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                colors = textFieldColors
            )
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChanged,
                placeholder = { Text(stringResource(id = R.string.description_hint)) },
                modifier = Modifier
                    .height(350.dp)
                    .fillMaxWidth(),
                colors = textFieldColors
            )
        }
    }
}