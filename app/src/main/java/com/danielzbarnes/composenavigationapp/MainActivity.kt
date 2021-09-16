package com.danielzbarnes.composenavigationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navDeepLink
import com.danielzbarnes.composenavigationapp.data.UserData
import com.danielzbarnes.composenavigationapp.ui.accounts.AccountsBody
import com.danielzbarnes.composenavigationapp.ui.accounts.SingleAccountBody
import com.danielzbarnes.composenavigationapp.ui.bills.BillsBody
import com.danielzbarnes.composenavigationapp.HomeScreen.*
import com.danielzbarnes.composenavigationapp.ui.components.BudgetTabRow
import com.danielzbarnes.composenavigationapp.ui.overview.OverviewBody
import com.danielzbarnes.composenavigationapp.ui.theme.BudgetAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetApp()
        }
    }
}

@Composable
fun BudgetApp() {
    BudgetAppTheme {



        val allScreens = HomeScreen.values().toList()
        val navController = rememberNavController()
        val backStackEntry = navController.currentBackStackEntryAsState()
        val currentScreen = HomeScreen.fromRoute(backStackEntry.value?.destination?.route)

        Scaffold(
            topBar = {
                BudgetTabRow(
                    allScreens = allScreens,
                    onTabSelected = { screen -> navController.navigate(screen.name)},
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->


            BudgetNavHost(navController, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun BudgetNavHost(navController: NavHostController, modifier: Modifier = Modifier){

    NavHost( navController = navController,
        startDestination = Overview.name, // compose navigation use strings to navigate
        modifier = modifier) {

        // simple navigation using the screen's name
        composable(Overview.name) { // main overview screen
            OverviewBody(
                onClickSeeAllAccounts = { navController.navigate(Accounts.name) },
                onClickSeeAllBills = { navController.navigate(Bills.name) },
                onAccountClick = { name -> navigateToSingleAccount(navController, name) }
            )
        }
        composable(Accounts.name) { // accounts screen
            AccountsBody(accounts = UserData.accounts){
                    name -> navigateToSingleAccount(navController, name)
            }
        }
        composable(Bills.name) { // bills screen
            BillsBody(bills = UserData.bills)
        }

        val accountsName = Accounts.name

        // navigation using an argument
        composable(route = "$accountsName/{name}",
            arguments = listOf(navArgument("name"){
                type = NavType.StringType
            }), deepLinks = listOf(navDeepLink { uriPattern = "budget://$accountsName/{name}" })
            // deepLinks allows 3rd party apps to access the app, requires intent-filter in the manifest
        ) {

                entry ->
            val accountName = entry.arguments?.getString("name")
            val account = UserData.getAccount(accountName)
            SingleAccountBody(account = account)
        }
    }
}


// navigation using an argument
private fun navigateToSingleAccount(navController: NavHostController, accountName: String){
    navController.navigate("${Accounts.name}/$accountName")
}
