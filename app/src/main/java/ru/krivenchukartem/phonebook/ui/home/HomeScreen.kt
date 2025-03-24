package ru.krivenchukartem.phonebook.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.krivenchukartem.phonebook.PhoneBookTopAppBar
import ru.krivenchukartem.phonebook.R
import ru.krivenchukartem.phonebook.data.Subscriber
import ru.krivenchukartem.phonebook.ui.AppViewModelProvider
import ru.krivenchukartem.phonebook.ui.navigation.NavigationDestination

object HomeDestination: NavigationDestination{
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToSubscriberEntry: () -> Unit,
    navigateToSubscriberEdit: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PhoneBookTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToSubscriberEntry,
                modifier = Modifier.padding(dimensionResource(R.dimen.large_padding))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.subscriber_entry_title)
                )
            }
        }
    ){ innerPadding ->
        HomeBody(
            subscribersList = homeUiState.subscribersList,
            onItemClick = navigateToSubscriberEdit,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeBody(
    subscribersList: List<Subscriber>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (subscribersList.isEmpty()){
            Text(
                text = stringResource(R.string.subscribers_list_is_empty),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        else(
            PhoneBookList(
                subscribersList = subscribersList,
                onItemClick = onItemClick,
                modifier = Modifier
            )
        )
    }
}

@Composable
fun PhoneBookList(
    subscribersList: List<Subscriber>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
    ){
        items(items = subscribersList, key = {it.id}){subscriber ->
            SubscriberItem(
                item = subscriber,
                modifier = Modifier
                    .clickable{onItemClick(subscriber.id)})
        }
    }
}

@Composable
fun SubscriberItem(
    item: Subscriber,
    modifier: Modifier = Modifier,
){
    Card (
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.medium_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
        ){
            Row {
                Text(text = stringResource(R.string.subscriber_name))
                Spacer(Modifier.weight(1f))
                Text(text = item.fullName)
            }
            Row{
                Text(text = stringResource(R.string.subscriber_number))
                Spacer(Modifier.weight(1f))
                Text(text = item.phoneNumber)
            }
        }
    }

}

@Preview
@Composable
fun HomeBodyPreview(){
    val list = listOf(
        Subscriber(
            id = 0,
            fullName = "Кривенчук Артем Александрович",
            phoneNumber = "81293129320"
        ),
        Subscriber(
            id = 1,
            fullName = "Вилисов Илья Дмитриевич",
            phoneNumber = "89133330340"
        ),
        Subscriber(
            id = 2,
            fullName = "Найман Алексей Евгеньевич",
            phoneNumber = "91234234902"
        )
    )
    HomeBody(
        subscribersList = listOf(),
        onItemClick = {}
    )
}

@Preview
@Composable
fun PhoneBookListPreview(){
    val list = listOf(
        Subscriber(
            id = 0,
            fullName = "Кривенчук Артем Александрович",
            phoneNumber = "81293129320"
        ),
        Subscriber(
            id = 1,
            fullName = "Вилисов Илья Дмитриевич",
            phoneNumber = "89133330340"
        ),
        Subscriber(
            id = 2,
            fullName = "Найман Алексей Евгеньевич",
            phoneNumber = "91234234902"
        )
    )
    PhoneBookList(
        subscribersList = list,
        onItemClick = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun SubscriberItemPreview(){
    SubscriberItem(
        Subscriber(
            fullName = "Керницких Михаил Алексеевич",
            phoneNumber = "89139130872"
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

