package ru.krivenchukartem.phonebook.ui.subscriber

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.krivenchukartem.phonebook.PhoneBookTopAppBar
import ru.krivenchukartem.phonebook.R
import ru.krivenchukartem.phonebook.ui.AppViewModelProvider
import ru.krivenchukartem.phonebook.ui.home.HomeBody
import ru.krivenchukartem.phonebook.ui.navigation.NavigationDestination

object SubscriberSearchDestination: NavigationDestination {
    override val route = "search"
    override val titleRes = R.string.subscriber_search_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriberSearchScreen(
    navigateToSubscriber: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: SubscriberSearchViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiSearchQuery by viewModel.uiQuery.collectAsState()
    val uiSubscribersList by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = { PhoneBookTopAppBar(
            title = stringResource(SubscriberSearchDestination.titleRes),
            canNavigateBack = true,
            navigationUp = navigateBack
        ) }
    ) { innerPadding ->
        SubscriberSearchBody(
            modifier = Modifier.padding(innerPadding),
            onSearchQueryChanged = viewModel::updateUiQuery,
            subscribersList = uiSubscribersList,
            navigateToSubscriber = navigateToSubscriber,
            searchQuery = uiSearchQuery
        )
    }
}

@Composable
fun SubscriberSearchBody(
    onSearchQueryChanged: (String) -> Unit,
    subscribersList: FoundSubscribersUiState,
    navigateToSubscriber: (Int) -> Unit,
    searchQuery: String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth().padding(dimensionResource(R.dimen.medium_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
    ) {
        TextField(
            onValueChange = onSearchQueryChanged,
            value = searchQuery,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )
        HomeBody(
            subscribersList = subscribersList.subscribersList,
            onItemClick = navigateToSubscriber,
            noneResult = R.string.subscriber_not_found,
        )
    }
}