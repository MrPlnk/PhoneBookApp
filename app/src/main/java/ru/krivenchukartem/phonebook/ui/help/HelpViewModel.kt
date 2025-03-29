package ru.krivenchukartem.phonebook.ui.help

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.krivenchukartem.phonebook.data.Subscriber
import ru.krivenchukartem.phonebook.data.SubscribersRepository


class HelpViewModel(private val subscribersRepository: SubscribersRepository): ViewModel() {
    val uiState: StateFlow<HelpUiState> = subscribersRepository.getAllSubscribersStream()
        .filterNotNull()
        .map { HelpUiState(subscribers = it, isNotEmpty = it.isNotEmpty()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HelpUiState()
        )

    companion object{
        private val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteAllSubscribers(){
        if (uiState.value.isNotEmpty){
            uiState.value.subscribers.forEach {
                subscribersRepository.deleteSubscriber(it)
            }
        }
    }

}

data class HelpUiState(
    val isNotEmpty: Boolean = false,
    val subscribers: List<Subscriber> = listOf()
)
