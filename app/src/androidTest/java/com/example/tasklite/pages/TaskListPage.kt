package com.example.tasklite.pages

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule

class TaskListPage(private val composeTestRule: ComposeContentTestRule) {

    fun clickAddTask() {
        composeTestRule.onNodeWithTag("fab_add_task").performClick()
    }

    fun verifyTaskExists(title: String) {
        composeTestRule.onNodeWithText(title).assertExists()
    }

    fun verifyTaskDoesNotExist(title: String) {
        composeTestRule.onNodeWithText(title).assertDoesNotExist()
    }

    fun markTaskCompleted(title: String) {
        composeTestRule.onNode(
            hasTestTag("task_checkbox") and hasAnyAncestor(hasTestTag("task_item") and hasAnyDescendant(hasText(title)))
        ).performClick()
    }

    fun verifyTaskIsCompleted(title: String) {
        composeTestRule.onNode(
            hasTestTag("completed_task") and hasAnyAncestor(hasTestTag("task_item") and hasAnyDescendant(hasText(title)))
        ).assertExists()
    }

    fun clickEdit(title: String) {
        composeTestRule.onNode(
            hasTestTag("edit_button") and hasAnyAncestor(hasTestTag("task_item") and hasAnyDescendant(hasText(title)))
        ).performClick()
    }

    fun clickDelete(title: String) {
        composeTestRule.onNode(
            hasTestTag("delete_button") and hasAnyAncestor(hasTestTag("task_item") and hasAnyDescendant(hasText(title)))
        ).performClick()
    }

    fun verifyTaskStatus(title: String, status: String) {
        composeTestRule.onNode(
            hasText("Estado: $status") and hasAnyAncestor(hasTestTag("task_item") and hasAnyDescendant(hasText(title)))
        ).assertExists()
    }

    fun verifyEmptyState() {
        composeTestRule.onNodeWithTag("empty_state").assertExists()
    }
}
