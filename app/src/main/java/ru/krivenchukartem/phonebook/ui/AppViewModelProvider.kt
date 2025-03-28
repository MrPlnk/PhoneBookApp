package ru.krivenchukartem.phonebook.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.krivenchukartem.phonebook.PhoneBookApplication
import ru.krivenchukartem.phonebook.ui.home.HomeViewModel
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberDetailScreen
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberDetailViewModel
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEditViewModel
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEntryField
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEntryScreen
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEntryViewModel
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberSearchViewModel

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

        initializer {
            SubscriberDetailViewModel(
                this.createSavedStateHandle(),
                phoneBookApplication().container.subscriberRepository,
            )
        }

        initializer {
            SubscriberEditViewModel(
                this.createSavedStateHandle(),
                phoneBookApplication().container.subscriberRepository
            )
        }

        initializer {
            SubscriberSearchViewModel(
                phoneBookApplication().container.subscriberRepository
            )
        }
    }
}

fun CreationExtras.phoneBookApplication(): PhoneBookApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PhoneBookApplication)
