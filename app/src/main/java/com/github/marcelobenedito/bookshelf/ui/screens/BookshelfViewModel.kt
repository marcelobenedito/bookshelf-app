package com.github.marcelobenedito.bookshelf.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.marcelobenedito.bookshelf.BookshelfApplication
import com.github.marcelobenedito.bookshelf.data.BookshelfRepository
import com.github.marcelobenedito.bookshelf.network.Book
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface BookshelfUiState {
    data class Success(val books: List<Book>): BookshelfUiState
    data object Failure: BookshelfUiState
    data object Loading: BookshelfUiState
}

class BookshelfViewModel(
    private val bookshelfRepository: BookshelfRepository
) : ViewModel() {

    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    var q: String by mutableStateOf("")
        private set

    var selectedBook: Book? by mutableStateOf(null)
        private set

    init {
        getBooks()
    }

    fun getBooks() {
        viewModelScope.launch {
            bookshelfUiState = try {
                BookshelfUiState.Success(books = bookshelfRepository.getBooks(q).items)
            } catch (e: IOException) {
                BookshelfUiState.Failure
            }
        }
    }

    fun onClickItem(book: Book) {
        selectedBook = book
    }

    fun onQChanged(value: String) {
        q = value
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.appContainer.bookshelfRepository
                BookshelfViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}