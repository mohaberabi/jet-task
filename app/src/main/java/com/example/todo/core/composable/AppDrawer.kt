package com.example.todo.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.todo.core.navigation.TodoNavigationActions
import kotlinx.coroutines.CoroutineScope
import java.nio.file.WatchEvent

typealias unitFunc = () -> Unit

@Composable
fun AppDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    onGoAddTask: unitFunc,
    onCloseDrawer: unitFunc,
    onGoOverview: unitFunc,
    content: @Composable () -> Unit
) {

    ModalNavigationDrawer(
        modifier = Modifier.background(Color.White),
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                DrawerButton(text = "Overview", onClick = {
                    onCloseDrawer()
                    onGoOverview()
                }, icon = Icons.Default.Info)
                DrawerButton(text = "Add Task", onClick = {
                    onCloseDrawer()
                    onGoAddTask()
                }, icon = Icons.Default.Add)

            }
        }
    ) {
        content()
    }

}


@Composable
fun DrawerButton(
    text: String,
    onClick: () -> Unit,
    icon: ImageVector,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clickable {
                onClick()
            },
    ) {

        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodySmall)
    }
}
