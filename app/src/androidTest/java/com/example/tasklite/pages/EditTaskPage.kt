package com.example.tasklite.pages

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule

class EditTaskPage(private val composeTestRule: ComposeContentTestRule) {

    fun updateTitle(newTitle: String) {
        composeTestRule.onNodeWithTag("task_title").performTextReplacement(newTitle)
    }

    fun updateDescription(newDescription: String) {
        composeTestRule.onNodeWithTag("task_description").performTextReplacement(newDescription)
    }

    fun selectStatus(status: String) {
        composeTestRule.onNodeWithText("Estado:", substring = true).performClick()
        composeTestRule.onNodeWithText(status).performClick()
    }

    fun clickUpdate() {
        composeTestRule.onNodeWithTag("update_button").performClick()
    }
}
