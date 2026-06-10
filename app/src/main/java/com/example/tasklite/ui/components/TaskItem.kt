package com.example.tasklite.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.tasklite.data.TaskEntity
import com.example.tasklite.data.TaskStatus

@Composable
fun TaskItem(
    task: TaskEntity,
    onComplete: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val isCompleted = task.status == TaskStatus.COMPLETED
    val contentColor = if (isCompleted) Color.Gray else Color.Black

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .testTag("task_item")
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isCompleted,
                onCheckedChange = { if (!isCompleted) onComplete() },
                modifier = Modifier.testTag("task_checkbox")
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .then(if (isCompleted) Modifier.testTag("completed_task") else Modifier)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = contentColor,
                    textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor
                )
                val statusText = when (task.status) {
                    TaskStatus.PENDING -> "Pendiente"
                    TaskStatus.IN_PROGRESS -> "En progreso"
                    TaskStatus.COMPLETED -> "Completada"
                }
                Text(
                    text = "Estado: $statusText",
                    style = MaterialTheme.typography.labelSmall,
                    color = contentColor,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            IconButton(onClick = onEdit, modifier = Modifier.testTag("edit_button")) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onDelete, modifier = Modifier.testTag("delete_button")) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
