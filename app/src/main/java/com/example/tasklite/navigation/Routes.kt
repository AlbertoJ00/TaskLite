package com.example.tasklite.navigation

object Routes {
    const val TASK_LIST = "task_list"
    const val ADD_TASK = "add_task"
    const val EDIT_TASK = "edit_task/{taskId}"

    fun editTaskWithId(taskId: Int) = "edit_task/$taskId"
}
