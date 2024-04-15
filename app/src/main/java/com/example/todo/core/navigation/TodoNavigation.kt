package com.example.todo.core.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.todo.core.navigation.TodoNavArgs.taskIdArg
import com.example.todo.core.navigation.TodoScreens.addTaskScreen
import com.example.todo.core.navigation.TodoScreens.statiScreen
import com.example.todo.core.navigation.TodoScreens.taskDetailScreen
import com.example.todo.core.navigation.TodoScreens.tasksScreen

object TodoScreens {


    const val tasksScreen = "tasksScreen"
    const val statiScreen = "statiScreen"
    const val taskDetailScreen = "taskDetailScreen"
    const val addTaskScreen = "addTaskScreen"
}

object TodoNavArgs {
    const val taskIdArg = "taskId"
}

object TodoDestinations {
    const val TASKS_ROUTE = tasksScreen
    const val STATISTICS_ROUTE = statiScreen
    const val TASK_DETAIL_ROUTE = "$taskDetailScreen/{$taskIdArg}"
    const val ADD_EDIT_TASK_ROUTE = "$addTaskScreen/?$taskIdArg={$taskIdArg}"
}

class TodoNavigationActions(private val navController: NavHostController) {


    fun navigateToStatistics() {
        navController.navigate(TodoDestinations.STATISTICS_ROUTE) {

            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToTaskDetail(taskId: String) {
        navController.navigate("$taskDetailScreen/$taskId")
    }

    fun navigateToAddEditTask(taskId: String?) {
        navController.navigate(
            "${addTaskScreen}/".let {
                if (taskId != null) "$it?$taskIdArg=$taskId" else it
            }
        )
    }
}
