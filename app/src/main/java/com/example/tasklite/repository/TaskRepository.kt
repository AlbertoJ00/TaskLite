package com.example.tasklite.repository

import com.example.tasklite.data.TaskDao
import com.example.tasklite.data.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<TaskEntity>> = taskDao.getAllTasks()

    suspend fun getTaskById(taskId: Int): TaskEntity? {
        return taskDao.getTaskById(taskId)
    }

    suspend fun insert(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun update(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: TaskEntity) {
        taskDao.deleteTask(task)
    }
}
