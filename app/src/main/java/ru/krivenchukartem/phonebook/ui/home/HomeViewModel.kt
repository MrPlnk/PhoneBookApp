package ru.krivenchukartem.phonebook.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.krivenchukartem.phonebook.data.Subscriber
import ru.krivenchukartem.phonebook.data.SubscribersRepository

class HomeViewModel(subscribersRepository: SubscribersRepository): ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        subscribersRepository.getAllSubscribersStream().map{ HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val subscribersList: List<Subscriber> = listOf())