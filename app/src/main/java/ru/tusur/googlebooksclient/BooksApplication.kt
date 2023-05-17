package ru.tusur.googlebooksclient

import android.app.Application
import ru.tusur.googlebooksclient.model.AppContainer
import ru.tusur.googlebooksclient.model.DefaultAppContainer

//subclass Application to initialise dependencies
class BooksApplication:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}