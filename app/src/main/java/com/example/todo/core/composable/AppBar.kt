package com.example.todo.core.composable

import android.graphics.Paint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    navIcon: ImageVector = Icons.Default.ArrowBack,
    onNavIconClick: () -> Unit = {},
    actions: List<@Composable () -> Unit> = emptyList()
) {

    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {

            IconButton(onClick = {
                onNavIconClick()
            }) {
                Icon(imageVector = navIcon, contentDescription = null)
            }
        }, actions = {
            actions.map {
                it()
            }
        }
    )


}