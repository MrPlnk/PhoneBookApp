package ru.krivenchukartem.phonebook.ui.subscriber

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.krivenchukartem.phonebook.R
import ru.krivenchukartem.phonebook.ui.AppViewModelProvider
import ru.krivenchukartem.phonebook.ui.navigation.NavigationDestination
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import ru.krivenchukartem.phonebook.PhoneBookTopAppBar
import java.nio.file.WatchEvent

object SubscriberEntryNavigation: NavigationDestination{
    override val route = "subscriber_entry"
    override val titleRes = R.string.subscriber_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriberEntryScreen(
    navigationBack: () -> Unit,
    navigationUp: () -> Unit,
    canNavigateBack: Boolean = true,
    entryViewModel: SubscriberEntryViewModel = viewModel(factory = AppViewModelProvider .Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { PhoneBookTopAppBar(
            title = stringResource(SubscriberEntryNavigation.titleRes),
            canNavigateBack = canNavigateBack,
            navigationUp = navigationUp
        ) }
    ){ innerPadding ->
        SubscriberEntryBody(
            subscriberUiState = entryViewModel.subscriberUiState,
            onSubscriberValueChanged = entryViewModel::updateState,
            onSaveButtonClick = {
                coroutineScope.launch {
                    entryViewModel.saveSubscriber()
                    navigationBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun SubscriberEntryBody(
    subscriberUiState: SubscriberUiState,
    onSubscriberValueChanged: (SubscriberDetail) -> Unit,
    onSaveButtonClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.medium_padding)),

    ){
        SubscriberEntryField(
            subscriberDetail = subscriberUiState.subscriberDetail,
            onValueChange = onSubscriberValueChanged,
            modifier = Modifier
        )
        Button(
            onClick = onSaveButtonClick,
            enabled = with(subscriberUiState){
                isNameValid && isNumberValid
            },
            modifier = Modifier.fillMaxWidth()
            ) {
            Text(text = stringResource(R.string.subscriber_save))
        }
    }
}

@Composable
fun SubscriberEntryField(
    subscriberDetail: SubscriberDetail,
    modifier: Modifier = Modifier,
    onValueChange: (SubscriberDetail) -> Unit = {},
    enabled: Boolean = true,
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
    ){
        OutlinedTextField(
            value = subscriberDetail.name,
            onValueChange = { onValueChange(subscriberDetail.copy(name = it)) },
            label = { Text(stringResource(R.string.subscriber_name_required)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = subscriberDetail.number,
            onValueChange = { onValueChange(subscriberDetail.copy(number = it)) },
            label = { Text(stringResource(R.string.subscriber_number_required)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled){
            Text(text = stringResource(R.string.required_field))
        }
    }

}

@Preview
@Composable
fun SubscriberEntryFieldPreview(){
    SubscriberEntryField(
        SubscriberDetail(0, "Кривенчук Артем Александрович", "812391238"),
        modifier = Modifier
    )
}