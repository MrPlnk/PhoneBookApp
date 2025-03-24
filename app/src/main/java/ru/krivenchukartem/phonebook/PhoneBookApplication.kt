package ru.krivenchukartem.phonebook

import android.app.Application
import ru.krivenchukartem.phonebook.data.AppContainer
import ru.krivenchukartem.phonebook.data.AppDataContainer

class PhoneBookApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}