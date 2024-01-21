package com.github.marcelobenedito.bookshelf.data

import com.github.marcelobenedito.bookshelf.network.AccessInfo
import com.github.marcelobenedito.bookshelf.network.Book
import com.github.marcelobenedito.bookshelf.network.VolumeInfo

object FakeDataSource {

    val books = listOf(
        Book(
            id = "1",
            volumeInfo = VolumeInfo(
                "Title 1",
                authors = listOf("Author 1"),
                categories = listOf("Category 1"),
                pageCount = 453,
                language = "en-US",
                description = "Lorem ipsum dolor sit amet. Et nobis accusantium et ipsa perferendis rem nulla debitis sed consectetur voluptatem et dignissimos totam qui commodi doloremque qui quidem sint."
            ),
            AccessInfo("")
        ),
        Book(
            id = "2",
            volumeInfo = VolumeInfo(
                "Title 2",
                authors = listOf("Author 2"),
                categories = listOf("Category 2")
            ),
            AccessInfo("")
        ),
        Book(
            id = "3",
            volumeInfo = VolumeInfo(
                "Title 3",
                authors = listOf("Author 3"),
                categories = listOf("Category 3")
            ),
            AccessInfo("")
        ),
        Book(
            id = "4",
            volumeInfo = VolumeInfo(
                "Title 4",
                authors = listOf("Author 4"),
                categories = listOf("Category 4")
            ),
            AccessInfo("")
        )
    )
}