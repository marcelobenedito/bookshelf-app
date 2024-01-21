package com.github.marcelobenedito.bookshelf.network

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    val volumeInfo: VolumeInfo,
    val accessInfo: AccessInfo
)

@Serializable
data class VolumeInfo(
    val title: String? = "",
    val description: String? = "",
    val authors: List<String>? = emptyList(),
    val publisher: String? = "",
    val publishedDate: String? = "",
    val pageCount: Int? = 0,
    val printType: String? = "",
    val categories: List<String>? = emptyList(),
    val imageLinks: ImageLinks? = ImageLinks(thumbnail = ""),
    val language: String? = ""
)

@Serializable
data class ImageLinks(val thumbnail: String)

@Serializable
data class AccessInfo(val webReaderLink: String)
