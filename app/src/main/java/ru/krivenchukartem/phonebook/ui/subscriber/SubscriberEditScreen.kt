package ru.krivenchukartem.phonebook.ui.subscriber

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ru.krivenchukartem.phonebook.PhoneBookTopAppBar
import ru.krivenchukartem.phonebook.R
import ru.krivenchukartem.phonebook.ui.AppViewModelProvider
import ru.krivenchukartem.phonebook.ui.navigation.NavigationDestination

object SubscriberEditDestination: NavigationDestination{
    override val route = "subscriber_edit"
    override val titleRes = R.string.subscriber_edit_title
    const val subscriberIdArg = "subscriberId"
    val routeWithArgs = "${route}/{$subscriberIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriberEditScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SubscriberEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier,
        topBar = {
            PhoneBookTopAppBar(
                title = stringResource(SubscriberEditDestination.titleRes),
                canNavigateBack = true,
                navigationUp = navigateBack
        ) }
    ){ innerPadding ->
        SubscriberEntryBody(
            subscriberUiState = viewModel.uiState,
            onSaveButtonClick = {
                coroutineScope.launch {
                    viewModel.updateSubscriber()
                    navigateBack()
                }
            },
            onSubscriberValueChanged = viewModel::updateUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
@Preview
fun SubscriberEditScreenPreview(){
    SubscriberEditScreen(
        navigateBack = {}
    )
}