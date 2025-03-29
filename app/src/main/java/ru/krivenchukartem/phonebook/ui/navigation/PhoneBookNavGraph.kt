package ru.krivenchukartem.phonebook.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.krivenchukartem.phonebook.R
import ru.krivenchukartem.phonebook.ui.help.HelpDestination
import ru.krivenchukartem.phonebook.ui.help.HelpScreen
import ru.krivenchukartem.phonebook.ui.home.HomeDestination
import ru.krivenchukartem.phonebook.ui.home.HomeScreen
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberDetailDestination
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberDetailScreen
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEditDestination
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEditScreen
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEntryNavigation
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberEntryScreen
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberSearchDestination
import ru.krivenchukartem.phonebook.ui.subscriber.SubscriberSearchScreen

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
                navigateToSubscriberDetail = {
                    navController.navigate("${SubscriberDetailDestination.route}/${it}")
                },
                navigateToSubscriberEntry = {navController.navigate(SubscriberEntryNavigation.route)},
                navigateToSubscriberSearch = {navController.navigate(SubscriberSearchDestination.route)},
                navigateToHelp = {navController.navigate(HelpDestination.route)}
            )
        }
        composable(
            route = SubscriberEditDestination.routeWithArgs,
            arguments = listOf(navArgument(SubscriberEditDestination.subscriberIdArg){
                type = NavType.IntType
            })
        ){
            SubscriberEditScreen(
                navigateBack = {navController.navigateUp()}
            )
        }
        composable(
            route = SubscriberDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(SubscriberDetailDestination.subscriberIdArg){
                type = NavType.IntType
            })
        ){
            SubscriberDetailScreen(
                navigateToSubscriberEdit = {
                    navController.navigate("${SubscriberEditDestination.route}/${it}")
                },
                navigateBack = {navController.navigateUp()}
            )
        }
        composable(route = SubscriberEntryNavigation.route){
            SubscriberEntryScreen(
                navigationBack = {navController.popBackStack()},
                navigationUp = {navController.navigateUp()}
            )
        }
        composable(route = SubscriberSearchDestination.route){
            SubscriberSearchScreen(
                navigateBack = {navController.navigateUp()},
                navigateToSubscriber = {
                    navController.navigate("${SubscriberDetailDestination.route}/${it}")
                }
            )
        }
        composable(route = HelpDestination.route){
            HelpScreen(
                navigationBack = {navController.navigateUp()}
            )
        }
    }
}