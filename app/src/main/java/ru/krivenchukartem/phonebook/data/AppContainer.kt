package ru.krivenchukartem.phonebook.data

import android.content.Context

interface AppContainer{
    val subscriberRepository: SubscribersRepository
}

class AppDataContainer(private val context: Context): AppContainer{
    override val subscriberRepository: SubscribersRepository by lazy{
        OfflineSubscribersRepository(PhoneBookDatabase.getDatabase(context).subscriberDao())
    }
}