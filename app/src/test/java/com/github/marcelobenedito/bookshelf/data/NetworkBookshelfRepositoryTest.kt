package com.github.marcelobenedito.bookshelf.data

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkBookshelfRepositoryTest {

    @Test
    fun networkBookshelfRepository_getBooks_verifyBookshelf() = runTest {
        val networkBookshelfRepository = NetworkBookshelfRepository(FakeBookshelfApiService())
        assertEquals(FakeDataSource.books, networkBookshelfRepository.getBooks("").items)
    }
}