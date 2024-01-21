package com.github.marcelobenedito.bookshelf.data

import com.github.marcelobenedito.bookshelf.network.AccessInfo
import com.github.marcelobenedito.bookshelf.network.Book
import com.github.marcelobenedito.bookshelf.network.VolumeInfo

object FakeDataSource {

    val books = listOf(
        Book(id = "1", volumeInfo = VolumeInfo("Title 1",  authors = listOf("Author 1"), categories = listOf("Category 1")), AccessInfo("")),
        Book(id = "2", volumeInfo = VolumeInfo("Title 2",  authors = listOf("Author 2"), categories = listOf("Category 2")), AccessInfo("")),
        Book(id = "3", volumeInfo = VolumeInfo("Title 3",  authors = listOf("Author 3"), categories = listOf("Category 3")), AccessInfo("")),
        Book(id = "4", volumeInfo = VolumeInfo("Title 4",  authors = listOf("Author 4"), categories = listOf("Category 4")), AccessInfo("")),
        Book(id = "5", volumeInfo = VolumeInfo("Title 5",  authors = listOf("Author 5"), categories = listOf("Category 5")), AccessInfo("")),
        Book(id = "6", volumeInfo = VolumeInfo("Title 6",  authors = listOf("Author 6"), categories = listOf("Category 6")), AccessInfo("")),
        Book(id = "7", volumeInfo = VolumeInfo("Title 7",  authors = listOf("Author 7"), categories = listOf("Category 7")), AccessInfo("")),
        Book(id = "8", volumeInfo = VolumeInfo("Title 8",  authors = listOf("Author 8"), categories = listOf("Category 8")), AccessInfo("")),
        Book(id = "9", volumeInfo = VolumeInfo("Title 9",  authors = listOf("Author 9"), categories = listOf("Category 9")), AccessInfo("")),
        Book(id = "10", volumeInfo = VolumeInfo("Title 10",  authors = listOf("Author 10"), categories = listOf("Category 10")), AccessInfo("")),
    )
}