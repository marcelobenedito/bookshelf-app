package com.github.marcelobenedito.bookshelf.data

import com.github.marcelobenedito.bookshelf.network.Bookshelf

class FakeNetworkBookshelfRepository : BookshelfRepository {
    override suspend fun getBooks(q: String): Bookshelf {
        return Bookshelf(items = FakeDataSource.books)
    }
}