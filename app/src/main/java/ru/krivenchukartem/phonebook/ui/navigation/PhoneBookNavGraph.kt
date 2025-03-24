package ru.krivenchukartem.phonebook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.krivenchukartem.phonebook.ui.home.HomeDestination
import ru.krivenchukartem.phonebook.ui.home.HomeScreen
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberDetailDestination
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberDetailScreen
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEditDestination
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEditScreen
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEntryNavigation
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEntryScreen

@Composable
fun PhoneBookNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ){
        composable(route = HomeDestination.route){
            HomeScreen(

            )
        }
        composable(route = SubscriberEditDestination.route){
            SubscriberEditScreen(

            )
        }
        composable(route = SubscriberDetailDestination.route){
            SubscriberDetailScreen(

            )
        }
        composable(route = SubscriberEntryNavigation.route){
            SubscriberEntryScreen(

            )
        }
    }
}