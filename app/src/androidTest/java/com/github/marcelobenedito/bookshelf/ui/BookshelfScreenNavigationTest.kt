package com.github.marcelobenedito.bookshelf.ui

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.github.marcelobenedito.bookshelf.util.assertCurrentRouteName
import com.github.marcelobenedito.bookshelf.R
import com.github.marcelobenedito.bookshelf.data.FakeDataSource
import com.github.marcelobenedito.bookshelf.data.FakeNetworkBookshelfRepository
import com.github.marcelobenedito.bookshelf.ui.screens.BookshelfViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BookshelfScreenNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupBookshelfNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            BookshelfApp(
                windowSizeClass = WindowWidthSizeClass.Compact,
                navController = navController,
                viewModel = BookshelfViewModel(FakeNetworkBookshelfRepository())
            )
        }
    }

    @Test
    fun bookshelfNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(BookshelfScreenEnum.Start.name)
    }

    @Test
    fun bookshelfNavHost_verifyBackNavigationNotShowOnStartScreen() {
        val backText = composeTestRule.activity.getString(R.string.back)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun bookshelfNavHost_clickOneBook_navigatesToDetailsScreen() {
        navigateToDetailsScreen()
        navController.assertCurrentRouteName(BookshelfScreenEnum.BookDetails.name)
    }

    @Test
    fun bookshelfNavHost_clickBackOnDetailsScreen_navigatesToStartScreen() {
        navigateToDetailsScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(BookshelfScreenEnum.Start.name)
    }

    private fun navigateToDetailsScreen() {
        composeTestRule.onNodeWithText(FakeDataSource.books[0].volumeInfo.title!!).performClick()
    }

    private fun performNavigateUp() {
        val back = composeTestRule.activity.getString(R.string.back)
        composeTestRule.onNodeWithContentDescription(back).performClick()
    }
}