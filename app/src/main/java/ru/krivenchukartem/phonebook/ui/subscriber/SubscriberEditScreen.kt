package ru.krivenchukartem.phonebook.ui.subscriber

import androidx.compose.runtime.Composable
import ru.krivenchukartem.phonebook.R
import ru.krivenchukartem.phonebook.ui.navigation.NavigationDestination

object SubscriberEditDestination: NavigationDestination{
    override val route = "subscriber_edit"
    override val titleRes = R.string.subscriber_edit_title
}

@Composable
fun SubscriberEditScreen() {
}