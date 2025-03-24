package ru.krivenchukartem.phonebook.ui.subscriber

import androidx.compose.runtime.Composable
import ru.krivenchukartem.phonebook.R
import ru.krivenchukartem.phonebook.ui.navigation.NavigationDestination

object SubscriberEntryNavigation: NavigationDestination{
    override val route = "subscriber_entry"
    override val titleRes = R.string.subscriber_entry_title
}

@Composable
fun SubscriberEntryScreen() {
}