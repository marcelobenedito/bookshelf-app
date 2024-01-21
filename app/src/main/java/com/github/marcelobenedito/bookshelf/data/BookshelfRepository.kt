package com.github.marcelobenedito.bookshelf.data

import com.github.marcelobenedito.bookshelf.network.Bookshelf
import com.github.marcelobenedito.bookshelf.network.BookshelfApiService

interface BookshelfRepository {
    suspend fun getBooks(q: String): Bookshelf
}

class NetworkBookshelfRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookshelfRepository {
    override suspend fun getBooks(q: String): Bookshelf {
        val parsedQ = if (q.isBlank()) {
            getRandomQ()
        } else {
            q.replace(" ", "+")
        }

        return bookshelfApiService.getBooks(parsedQ)
    }

    private fun getRandomQ(): String {
        return listOf(
            "Fantasy",
            "Adventure",
            "Romance",
            "Contemporary",
            "Dystopian",
            "Mystery",
            "Horror",
            "Thriller",
            "Paranormal",
            "Historical fiction",
            "Science Fiction",
            "Children’s",
            "Memoir",
            "Cookbook",
            "Art",
            "Self-help",
            "Personal Development",
            "Motivational",
            "Health",
            "History",
            "Travel",
            "Guide How-to",
            "Families & Relationships",
            "Humor"
        )
        .shuffled()
        .first()
        .replace(" ", "+")
    }
}
