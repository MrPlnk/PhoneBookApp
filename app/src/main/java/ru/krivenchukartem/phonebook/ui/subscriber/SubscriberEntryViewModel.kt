package ru.krivenchukartem.phonebook.ui.subscriber

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import ru.krivenchukartem.phonebook.data.SubscribersRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import ru.krivenchukartem.phonebook.data.Subscriber

class SubscriberEntryViewModel(private val subscribersRepository: SubscribersRepository): ViewModel() {
    var subscriberUiState by mutableStateOf(SubscriberUiState())
        private set

    fun updateState(newValue: SubscriberDetail){
        subscriberUiState = SubscriberUiState(
            isNameValid = validateName(newValue),
            isNumberValid = validateNumber(newValue),
            subscriberDetail = newValue
        )
    }

    private fun validateName(subscriberDetail: SubscriberDetail = subscriberUiState.subscriberDetail): Boolean {
        return with(subscriberDetail){
            name.isNotBlank()
        }
    }

    private fun validateNumber(subscriberDetail: SubscriberDetail = subscriberUiState.subscriberDetail): Boolean {
        return with(subscriberDetail) {
            number.all { symbol: Char -> symbol.isDigit() || symbol == '+' } &&
                    number.isNotBlank()
            }
    }

    suspend fun saveSubscriber(){
        if (validateNumber() && validateName()) {
            subscribersRepository.insertSubscriber(subscriberUiState.subscriberDetail.toSubscriber())
        }
    }
}

data class SubscriberUiState(
    val isNameValid: Boolean = false,
    val isNumberValid: Boolean = false,
    val subscriberDetail: SubscriberDetail = SubscriberDetail()
)

data class SubscriberDetail(
    val id: Int = 0,
    val name: String = "",
    val number: String = "",
)

fun SubscriberDetail.toSubscriber(): Subscriber = Subscriber(
    id = id,
    fullName = name,
    phoneNumber = number
)