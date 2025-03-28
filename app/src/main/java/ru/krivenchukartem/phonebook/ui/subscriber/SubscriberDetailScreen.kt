package ru.krivenchukartem.phonebook.ui.subscriber

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ru.krivenchukartem.phonebook.PhoneBookTopAppBar
import ru.krivenchukartem.phonebook.R
import ru.krivenchukartem.phonebook.ui.AppViewModelProvider
import ru.krivenchukartem.phonebook.ui.navigation.NavigationDestination

object SubscriberDetailDestination: NavigationDestination{
    override val route = "subscriber_detail"
    override val titleRes = R.string.subscriber_detail_title
    const val subscriberIdArg = "subscriberId"
    val routeWithArgs = "$route/{$subscriberIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriberDetailScreen(
    navigateToSubscriberEdit: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SubscriberDetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            PhoneBookTopAppBar(
                title = stringResource(SubscriberDetailDestination.titleRes),
                canNavigateBack = true,
                navigationUp = navigateBack
        ) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navigateToSubscriberEdit(uiState.value.subscriberDetail.id)},
                modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding))
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.subscriber_edit_title)
                )
            }
        }
    ){ innerPadding ->
        SubscriberDetailBody(
            onCallButtonClicked = {viewModel.dialSubscriber(context)},
            onDeleteButtonClicked = {
                coroutineScope.launch {
                    viewModel.deleteSubscriber()
                    navigateBack()
                }
            },
            uiState = uiState.value,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun SubscriberDetailBody(
    onCallButtonClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
    uiState: SubscriberDetailUiState,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.medium_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium_padding))
    ){
        SubscriberDetailCard(
            subscriberDetail = uiState.subscriberDetail
        )
        Button(
            onClick = onCallButtonClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.subscriber_call))
        }
        OutlinedButton(
            onClick = onDeleteButtonClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.subscriber_delete))
        }
    }
}

@Composable
fun SubscriberDetailCard(
    subscriberDetail: SubscriberDetail,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
    ){
        Column(
            modifier = modifier.padding(dimensionResource(R.dimen.medium_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
        ){
            Text(text = subscriberDetail.name)
            HorizontalDivider()
            Text(text = subscriberDetail.number)
        }
    }
}



@Composable
@Preview
fun SubscriberDetailCardPreview(){
    SubscriberDetailCard(
        SubscriberDetail(0, "Кривенчук Артем Александрович", "2384299233")
    )
}