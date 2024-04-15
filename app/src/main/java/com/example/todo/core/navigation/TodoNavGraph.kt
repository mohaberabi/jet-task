package com.example.todo.core.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo.core.composable.AppDrawer
import com.example.todo.feature.addtask.screen.AddTaskScreen
import com.example.todo.feature.task.screen.TasksScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ToDoNabGraph(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    start: String = TodoDestinations.TASKS_ROUTE,
    navActions: TodoNavigationActions = remember(navController) {
        TodoNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: start

    NavHost(navController = navController, startDestination = start) {


        composable(TodoDestinations.TASKS_ROUTE) {

            AppDrawer(
                drawerState = drawerState,
                currentRoute = currentRoute,

                onCloseDrawer = {

                    coroutineScope.launch {
                        drawerState.close()
                    }


                },
                onGoAddTask = {
                    navActions.navigateToAddEditTask(taskId = null)

                },
                onGoOverview = {},
            ) {
                TasksScreen(
                    openDrawer = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    },
                    onAddTask = {

                        navActions.navigateToAddEditTask(taskId = null)
                    },
                    onTaskClick = {
                        navActions.navigateToAddEditTask(taskId = it.id)

                    }
                )
            }
        }

        composable(
            TodoDestinations.ADD_EDIT_TASK_ROUTE,
            arguments = listOf(
                navArgument(TodoNavArgs.taskIdArg) {
                    type = NavType.StringType; nullable = true
                },
            )
        ) {

                entry ->
            val taskId = entry.arguments?.getString(TodoNavArgs.taskIdArg)

            AddTaskScreen(
                taskId = taskId,
                onTaskUpdate = {
                    navController.popBackStack()

                }, onBack = {
                    navController.popBackStack()
                }
            )

        }
    }
}