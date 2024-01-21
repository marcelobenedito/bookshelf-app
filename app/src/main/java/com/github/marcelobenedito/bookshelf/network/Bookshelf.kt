package com.github.marcelobenedito.bookshelf.network

import kotlinx.serialization.Serializable

@Serializable
data class Bookshelf(val items: List<Book>)
