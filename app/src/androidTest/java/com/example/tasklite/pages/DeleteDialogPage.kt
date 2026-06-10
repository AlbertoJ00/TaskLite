package com.example.tasklite.pages

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule

class DeleteDialogPage(private val composeTestRule: ComposeContentTestRule) {

    fun verifyDialogVisible() {
        composeTestRule.onNodeWithTag("delete_dialog", useUnmergedTree = true).assertExists()
    }

    fun confirmDelete() {
        composeTestRule.onNodeWithTag("confirm_delete", useUnmergedTree = true).performClick()
    }

    fun cancelDelete() {
        composeTestRule.onNodeWithTag("cancel_delete", useUnmergedTree = true).performClick()
    }
}
