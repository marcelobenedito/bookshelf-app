package com.github.marcelobenedito.bookshelf.data

import com.github.marcelobenedito.bookshelf.network.Bookshelf
import com.github.marcelobenedito.bookshelf.network.BookshelfApiService

class FakeBookshelfApiService : BookshelfApiService {
    override suspend fun getBooks(q: String): Bookshelf {
        return Bookshelf(items = FakeDataSource.books)
    }
}