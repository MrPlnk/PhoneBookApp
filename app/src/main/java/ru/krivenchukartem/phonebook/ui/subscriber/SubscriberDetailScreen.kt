package ru.krivenchukartem.phonebook.ui.subscriber

import androidx.compose.runtime.Composable
import ru.krivenchukartem.phonebook.R
import ru.krivenchukartem.phonebook.ui.navigation.NavigationDestination

object SubscriberDetailDestination: NavigationDestination{
    override val route = "subscriber_detail"
    override val titleRes = R.string.subscriber_detail_title
}

@Composable
fun SubscriberDetailScreen() {
}