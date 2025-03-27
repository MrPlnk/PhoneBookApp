package ru.krivenchukartem.phonebook.ui.subscriber

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.krivenchukartem.phonebook.data.Subscriber
import ru.krivenchukartem.phonebook.data.SubscribersRepository

class SubscriberSearchViewModel(
    private val subscribersRepository: SubscribersRepository
): ViewModel() {

    private val _uiQuery = MutableStateFlow("")
    val uiQuery: StateFlow<String> = _uiQuery.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<FoundSubscribersUiState> = _uiQuery
        .flatMapLatest { query ->
            subscribersRepository.getSubscribersByNameStream(query)
                .filterNotNull()
                .map { FoundSubscribersUiState(subscribersList = it) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = FoundSubscribersUiState()
        )

    companion object{
        private val TIMEOUT_MILLIS = 300L
        private val DEBOUNCE_MILLIS = 5_000L
    }

    fun updateUiQuery(newValue: String){
        _uiQuery.value = newValue
    }
}

data class FoundSubscribersUiState(
    val subscribersList: List<Subscriber> = listOf()
)