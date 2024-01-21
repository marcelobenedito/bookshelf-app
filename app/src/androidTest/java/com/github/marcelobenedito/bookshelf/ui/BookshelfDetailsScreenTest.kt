package com.github.marcelobenedito.bookshelf.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.github.marcelobenedito.bookshelf.data.FakeDataSource
import com.github.marcelobenedito.bookshelf.R
import com.github.marcelobenedito.bookshelf.ui.screens.BookDetailsScreen
import org.junit.Rule
import org.junit.Test

class BookshelfDetailsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun detailsScreen_verifyContent() {
        val book = FakeDataSource.books[0]

        // When DetailsScreen is loaded
        composeTestRule.setContent {
            BookDetailsScreen(book = book)
        }

        // Then all info are displayed
        composeTestRule.onNodeWithText(book.volumeInfo.title!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(book.volumeInfo.authors!!.joinToString { ", " })
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.page_count,
                book.volumeInfo.pageCount
            )
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(book.volumeInfo.language!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(book.volumeInfo.description!!).assertIsDisplayed()
    }
}