package com.example.tasklite.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tasklite.ui.screens.AddTaskScreen
import com.example.tasklite.ui.screens.EditTaskScreen
import com.example.tasklite.ui.screens.TaskListScreen
import com.example.tasklite.viewmodel.TaskViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: TaskViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.TASK_LIST
    ) {
        composable(Routes.TASK_LIST) {
            TaskListScreen(
                viewModel = viewModel,
                onAddTask = { navController.navigate(Routes.ADD_TASK) },
                onEditTask = { taskId -> navController.navigate(Routes.editTaskWithId(taskId)) }
            )
        }
        composable(Routes.ADD_TASK) {
            AddTaskScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Routes.EDIT_TASK,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            EditTaskScreen(
                taskId = taskId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
