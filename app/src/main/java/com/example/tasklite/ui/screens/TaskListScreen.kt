package com.example.tasklite.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.tasklite.data.TaskEntity
import com.example.tasklite.ui.components.TaskItem
import com.example.tasklite.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onAddTask: () -> Unit,
    onEditTask: (Int) -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    var taskToDelete by remember { mutableStateOf<TaskEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("TaskLite") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTask,
                modifier = Modifier.testTag("fab_add_task")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (tasks.isEmpty()) {
                Text(
                    text = "No tasks available",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("empty_state")
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("task_list")
                ) {
                    items(tasks) { task ->
                        TaskItem(
                            task = task,
                            onComplete = { viewModel.completeTask(task) },
                            onEdit = { onEditTask(task.id) },
                            onDelete = { taskToDelete = task }
                        )
                    }
                }
            }
        }
    }

    if (taskToDelete != null) {
        AlertDialog(
            onDismissRequest = { taskToDelete = null },
            title = { Text("Delete Task") },
            text = { Text("Are you sure you want to delete this task?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        taskToDelete?.let { viewModel.deleteTask(it) }
                        taskToDelete = null
                    },
                    modifier = Modifier.testTag("confirm_delete")
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { taskToDelete = null },
                    modifier = Modifier.testTag("cancel_delete")
                ) {
                    Text("Cancel")
                }
            },
            modifier = Modifier.testTag("delete_dialog")
        )
    }
}
