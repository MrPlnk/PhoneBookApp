package ru.krivenchukartem.phonebook

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.krivenchukartem.phonebook.ui.navigation.NavigationDestination
import ru.krivenchukartem.phonebook.ui.navigation.PhoneBookNavHost

@Composable
fun PhoneBookApp(navController: NavHostController = rememberNavController()){
    PhoneBookNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneBookTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationUp: () -> Unit = {},
    canSearch: Boolean = false,
    navigationToSearch: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = { Text(text = title) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigationUp) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        actions = {
            if (canSearch) {
                IconButton(onClick = navigationToSearch) {
                    Icon(
                        imageVector = Filled.Search,
                        contentDescription = stringResource(R.string.subscriber_search_title)
                    )
                }
            }
        }
    )
}