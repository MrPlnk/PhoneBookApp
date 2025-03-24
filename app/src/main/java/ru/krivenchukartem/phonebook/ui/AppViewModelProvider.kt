package ru.krivenchukartem.phonebook.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.krivenchukartem.phonebook.PhoneBookApplication
import ru.krivenchukartem.phonebook.ui.home.HomeViewModel
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEntryField
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEntryScreen
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            HomeViewModel(
                phoneBookApplication().container.subscriberRepository
            )
        }

        initializer {
            SubscriberEntryViewModel(
                phoneBookApplication().container.subscriberRepository
            )
        }
    }
}

fun CreationExtras.phoneBookApplication(): PhoneBookApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PhoneBookApplication)
