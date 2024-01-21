package com.github.marcelobenedito.bookshelf.ui

import com.github.marcelobenedito.bookshelf.data.FakeDataSource
import com.github.marcelobenedito.bookshelf.data.FakeNetworkBookshelfRepository
import com.github.marcelobenedito.bookshelf.util.rules.TestDispatcherRule
import com.github.marcelobenedito.bookshelf.ui.screens.BookshelfUiState
import com.github.marcelobenedito.bookshelf.ui.screens.BookshelfViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BookshelfViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun bookshelfViewModel_getBooks_verifyBookshelfUiStateSuccess() = runTest {
        val bookshelfViewModel = BookshelfViewModel(
            bookshelfRepository = FakeNetworkBookshelfRepository()
        )
        assertEquals(BookshelfUiState.Success(FakeDataSource.books), bookshelfViewModel.bookshelfUiState)
    }
}