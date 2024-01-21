package com.github.marcelobenedito.bookshelf

import android.app.Application
import com.github.marcelobenedito.bookshelf.data.AppContainer
import com.github.marcelobenedito.bookshelf.data.DefaultAppContainer

class BookshelfApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer()
    }
}