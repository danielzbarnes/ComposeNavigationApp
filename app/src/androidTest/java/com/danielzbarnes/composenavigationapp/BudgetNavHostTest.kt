package com.danielzbarnes.composenavigationapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import junit.framework.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BudgetNavHostTest {

    @get: Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: NavHostController

    @Before
    fun setupBudgetNavHost(){
        composeTestRule.setContent {
            navController = rememberNavController()
            BudgetNavHost(navController = navController)
        }
    }

    @Test
    fun budgetNavHost(){
        composeTestRule.onNodeWithContentDescription("Overview Screen").assertIsDisplayed()
    }

}