package ru.krivenchukartem.phonebook.ui.subscriber

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.krivenchukartem.phonebook.data.Subscriber
import ru.krivenchukartem.phonebook.data.SubscribersRepository

class SubscriberEditViewModel(
    val savedStateHandle: SavedStateHandle,
    private val subscribersRepository: SubscribersRepository
): ViewModel() {
    private val subscriberId: Int = checkNotNull(savedStateHandle[SubscriberEditDestination.subscriberIdArg])

    var uiState by mutableStateOf(SubscriberUiState())
        private set

    init {
        viewModelScope.launch {
            uiState = subscribersRepository.getSubscriberStream(subscriberId)
                .filterNotNull()
                .first()
                .toSubscriberUiState(
                    isNameValid = true,
                    isNumberValid = true
                )
        }
    }

    fun updateUiState(subscriberDetail: SubscriberDetail){
        uiState = SubscriberUiState(
            isNameValid = validateName(subscriberDetail),
            isNumberValid = validateNumber(subscriberDetail),
            subscriberDetail = subscriberDetail
        )
    }

    suspend fun updateSubscriber(){
        if (validateName() && validateNumber()){
            subscribersRepository.updateSubscriber(subscriber = uiState.subscriberDetail.toSubscriber())
        }
    }

    private fun validateName(subscriberDetail: SubscriberDetail = uiState.subscriberDetail): Boolean {
        return with(subscriberDetail){
            name.isNotBlank()
        }
    }

    private fun validateNumber(subscriberDetail: SubscriberDetail = uiState.subscriberDetail): Boolean {
        return with(subscriberDetail) {
            number.all { symbol: Char -> symbol.isDigit() || symbol == '+' } &&
                    number.isNotBlank()
        }
    }
}

