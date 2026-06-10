package com.example.tasklite.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.tasklite.data.TaskStatus
import com.example.tasklite.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    taskId: Int,
    viewModel: TaskViewModel,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(TaskStatus.PENDING) }
    var expanded by remember { mutableStateOf(false) }
    val taskState = remember { mutableStateOf<com.example.tasklite.data.TaskEntity?>(null) }

    LaunchedEffect(taskId) {
        val task = viewModel.getTaskById(taskId)
        task?.let {
            taskState.value = it
            title = it.title
            description = it.description
            status = it.status
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Task") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("task_title")
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("task_description")
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                val statusText = when (status) {
                    TaskStatus.PENDING -> "Pendiente"
                    TaskStatus.IN_PROGRESS -> "En progreso"
                    TaskStatus.COMPLETED -> "Completada"
                }
                OutlinedCard(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Estado: $statusText")
                        Text("▼")
                    }
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    TaskStatus.entries.forEach { entry ->
                        val entryText = when (entry) {
                            TaskStatus.PENDING -> "Pendiente"
                            TaskStatus.IN_PROGRESS -> "En progreso"
                            TaskStatus.COMPLETED -> "Completada"
                        }
                        DropdownMenuItem(
                            text = { Text(entryText) },
                            onClick = {
                                status = entry
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    taskState.value?.let {
                        viewModel.updateTask(it.copy(title = title, description = description, status = status))
                        onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("update_button")
            ) {
                Text("Update")
            }
        }
    }
}
