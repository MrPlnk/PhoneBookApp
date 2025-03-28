package ru.krivenchukartem.phonebook.ui.subscriber

import android.Manifest
import android.app.Activity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.krivenchukartem.phonebook.data.Subscriber
import ru.krivenchukartem.phonebook.data.SubscribersRepository
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SubscriberDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val subscribersRepository: SubscribersRepository,
): ViewModel() {

    private val subscriberId: Int = checkNotNull(savedStateHandle[SubscriberDetailDestination.subscriberIdArg])

    val uiState: StateFlow<SubscriberDetailUiState> = subscribersRepository.getSubscriberStream(subscriberId)
        .filterNotNull()
        .map {
            SubscriberDetailUiState(subscriberDetail = it.toSubscriberDetail())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = SubscriberDetailUiState()
    )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteSubscriber(){
        subscribersRepository.deleteSubscriber(uiState.value.subscriberDetail.toSubscriber())
    }

    fun dialSubscriber(context: Context) {
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${uiState.value.subscriberDetail.number}")
        }
        context.startActivity(dialIntent)
    }



}

data class SubscriberDetailUiState(
    val subscriberDetail: SubscriberDetail = SubscriberDetail()
)