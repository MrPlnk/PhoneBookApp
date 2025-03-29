package ru.krivenchukartem.phonebook.ui.help

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.krivenchukartem.phonebook.ui.AppViewModelProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.util.TableInfo
import kotlinx.coroutines.launch
import ru.krivenchukartem.phonebook.PhoneBookTopAppBar
import ru.krivenchukartem.phonebook.R
import ru.krivenchukartem.phonebook.ui.navigation.NavigationDestination

object HelpDestination: NavigationDestination{
    override val route = "help"
    override val titleRes = R.string.help_screen_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(
    navigationBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HelpViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        topBar = { PhoneBookTopAppBar(
            title = stringResource(HelpDestination.titleRes),
            canNavigateBack = true,
            navigationUp = navigationBack
        ) }
    ){ innerPadding ->
        HelpBody(
            uiState = uiState,
            deleteSubscribers = {
                coroutineScope.launch {
                    viewModel.deleteAllSubscribers()
                }
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HelpBody(
    uiState: HelpUiState,
    deleteSubscribers: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.medium_padding))
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
            ) {
                Text(text = stringResource(R.string.app_about))
            }

        }

        DeleteSubscribersCard(
            deleteSubscribers = deleteSubscribers,
            isPhoneBookEmpty = uiState.isNotEmpty
        )
    }
}

@Composable
fun DeleteSubscribersCard(
    deleteSubscribers: () -> Unit,
    isPhoneBookEmpty: Boolean,
    modifier: Modifier = Modifier
){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
    ){
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.small_padding))
        ) {
            Text(
                text = if (isPhoneBookEmpty) stringResource(R.string.delete_all_subscribers)
                else stringResource(R.string.subscriber_not_found),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small_padding)))
            Button(
                onClick = {deleteConfirmationRequired = true},
                enabled = isPhoneBookEmpty,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.help_delete),
                )
            }
            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        deleteSubscribers()
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false },
                    modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding))
                )
            }
        }

    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_all_subscribers)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes))
            }
        })
}

@Preview
@Composable
fun HelpBodyEmptyPreview(){
    HelpBody(
        uiState = HelpUiState(),
        deleteSubscribers = {}
    )
}