package com.example.tasklite.tests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.tasklite.MainActivity
import com.example.tasklite.pages.*
import org.junit.Rule
import org.junit.Test

class TaskLiteTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val taskListPage = TaskListPage(composeTestRule)
    private val addTaskPage = AddTaskPage(composeTestRule)
    private val editTaskPage = EditTaskPage(composeTestRule)
    private val deleteDialogPage = DeleteDialogPage(composeTestRule)

    private fun pause() {
        Thread.sleep(2000)
    }

    @Test
    fun test1_createTask() {
        taskListPage.clickAddTask()
        pause()
        addTaskPage.enterTitle("Implementar login")
        pause()
        addTaskPage.enterDescription("Task Description")
        pause()
        addTaskPage.selectStatus("En progreso")
        pause()
        addTaskPage.clickSave()
        pause()
        taskListPage.verifyTaskExists("Implementar login")
        taskListPage.verifyTaskStatus("Implementar login", "En progreso")

    }

    @Test
    fun test2_formValidation() {
        taskListPage.clickAddTask()
        pause()
        addTaskPage.clickSave()
        pause()
        addTaskPage.verifyTitleError("Title is required")
        addTaskPage.verifyDescriptionError("Description is required")
    }

    @Test
    fun test3_completeTask() {
        taskListPage.clickAddTask()
        addTaskPage.enterTitle("Task to Complete")
        addTaskPage.enterDescription("Description")
        addTaskPage.clickSave()
        pause()
        
        taskListPage.markTaskCompleted("Task to Complete")
        pause()
        taskListPage.verifyTaskIsCompleted("Task to Complete")
        taskListPage.verifyTaskStatus("Task to Complete", "Completada")
    }

    @Test
    fun test4_editTask() {
        taskListPage.clickAddTask()
        addTaskPage.enterTitle("Task to Edit")
        addTaskPage.enterDescription("Original Description")
        addTaskPage.clickSave()
        pause()
        
        taskListPage.clickEdit("Task to Edit")
        pause()
        editTaskPage.updateTitle("Edited Task")
        pause()
        editTaskPage.updateDescription("Hola a todos")
        pause()
        editTaskPage.selectStatus("En progreso")
        pause()
        editTaskPage.clickUpdate()
        pause()
        
        taskListPage.verifyTaskExists("Edited Task")
        taskListPage.verifyTaskStatus("Edited Task", "En progreso")
        taskListPage.verifyTaskDoesNotExist("Task to Edit")
    }

    @Test
    fun test5_deleteTask() {
        taskListPage.clickAddTask()
        addTaskPage.enterTitle("Task to Delete")
        addTaskPage.enterDescription("Description")
        addTaskPage.clickSave()
        pause()
        
        taskListPage.clickDelete("Task to Delete")
        pause()
        deleteDialogPage.verifyDialogVisible()
        pause()
        deleteDialogPage.confirmDelete()
        pause()
        
        taskListPage.verifyTaskDoesNotExist("Task to Delete")
    }

    @Test
    fun test6_persistenceTest() {
        val title = "Persistent Task"
        taskListPage.clickAddTask()
        addTaskPage.enterTitle(title)
        addTaskPage.enterDescription("Should survive recreation")
        addTaskPage.clickSave()
        pause()
        
        composeTestRule.activityRule.scenario.recreate()
        pause()
        
        taskListPage.verifyTaskExists(title)
        taskListPage.verifyTaskStatus(title, "Pendiente")
    }
}
