package com.example.tasklite.pages

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule

class AddTaskPage(private val composeTestRule: ComposeContentTestRule) {

    fun enterTitle(title: String) {
        composeTestRule.onNodeWithTag("task_title").performClick().performTextReplacement(title)
    }

    fun enterDescription(description: String) {
        composeTestRule.onNodeWithTag("task_description").performClick().performTextReplacement(description)
    }

    fun selectStatus(status: String) {
        composeTestRule.onNodeWithText("Estado:", substring = true).performClick()
        composeTestRule.onNodeWithText(status).performClick()
    }

    fun clickSave() {
        composeTestRule.onNodeWithTag("save_button").performClick()
    }

    fun verifyTitleError(error: String) {
        composeTestRule.onNodeWithTag("title_error", useUnmergedTree = true).assertTextEquals(error)
    }

    fun verifyDescriptionError(error: String) {
        composeTestRule.onNodeWithTag("description_error", useUnmergedTree = true).assertTextEquals(error)
    }
}
