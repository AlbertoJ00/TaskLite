package com.example.tasklite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasklite.data.TaskEntity
import com.example.tasklite.data.TaskStatus
import com.example.tasklite.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val tasks: StateFlow<List<TaskEntity>> = repository.allTasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTask(title: String, description: String, status: TaskStatus = TaskStatus.PENDING, onResult: (Boolean, String?, String?) -> Unit) {
        val titleError = if (title.isBlank()) "Title is required" else null
        val descriptionError = if (description.isBlank()) "Description is required" else null

        if (titleError == null && descriptionError == null) {
            viewModelScope.launch {
                repository.insert(TaskEntity(title = title, description = description, status = status))
                onResult(true, null, null)
            }
        } else {
            onResult(false, titleError, descriptionError)
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.update(task)
        }
    }

    fun completeTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.update(task.copy(status = TaskStatus.COMPLETED))
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    suspend fun getTaskById(taskId: Int): TaskEntity? {
        return repository.getTaskById(taskId)
    }
}
