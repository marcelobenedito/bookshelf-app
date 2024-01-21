package com.github.marcelobenedito.bookshelf.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.github.marcelobenedito.bookshelf.data.FakeDataSource
import com.github.marcelobenedito.bookshelf.network.Book
import com.github.marcelobenedito.bookshelf.ui.screens.BookshelfScreen
import com.github.marcelobenedito.bookshelf.ui.screens.BookshelfUiState
import org.junit.Rule
import org.junit.Test

class BookshelfStartScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun startScreen_verifyContent() {
        val books: List<Book> = FakeDataSource.books

        // When StartScreen is loaded
        composeTestRule.setContent { 
            BookshelfScreen(
                bookshelfUiState = BookshelfUiState.Success(books),
                onClickItem = {},
                onQChange = {},
                getBooks = {}
            )
        }

        // Then all books are displayed
        books.forEach { book ->
            composeTestRule.onNodeWithText(book.volumeInfo.title!!).assertIsDisplayed()
        }
    }
}