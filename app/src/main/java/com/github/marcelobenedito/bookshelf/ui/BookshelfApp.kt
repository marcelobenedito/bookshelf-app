package com.github.marcelobenedito.bookshelf.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.marcelobenedito.bookshelf.R
import com.github.marcelobenedito.bookshelf.ui.screens.BookDetailsScreen
import com.github.marcelobenedito.bookshelf.ui.screens.BookshelfScreen
import com.github.marcelobenedito.bookshelf.ui.screens.BookshelfViewModel

enum class BookshelfScreenEnum(@StringRes val title: Int) {
    Start(title = R.string.bookshelf),
    BookDetails(title = R.string.book_details)
}

enum class BookshelfScreenType {
    LIST_ONLY, LIST_AND_DETAILS
}

@Composable
fun BookshelfApp(
    windowSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory)
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BookshelfScreenEnum.valueOf(
        backStackEntry?.destination?.route ?: BookshelfScreenEnum.Start.name
    )
    val screenType: BookshelfScreenType = when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> BookshelfScreenType.LIST_ONLY
        WindowWidthSizeClass.Medium -> BookshelfScreenType.LIST_ONLY
        WindowWidthSizeClass.Expanded -> BookshelfScreenType.LIST_AND_DETAILS
        else -> BookshelfScreenType.LIST_ONLY
    }

    Scaffold(
        topBar = { BookshelfAppBar(
            currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() }
        ) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BookshelfScreenEnum.Start.name
        ) {
            composable(route = BookshelfScreenEnum.Start.name) {
                if (screenType == BookshelfScreenType.LIST_ONLY) {
                    BookshelfScreen(
                        bookshelfUiState = viewModel.bookshelfUiState,
                        selectedBook = viewModel.selectedBook,
                        q = viewModel.q,
                        onClickItem = {
                            viewModel.onClickItem(it)
                            navController.navigate(BookshelfScreenEnum.BookDetails.name)
                        },
                        onQChange = viewModel::onQChanged,
                        getBooks = viewModel::getBooks,
                        modifier = modifier.padding(innerPadding)
                    )
                } else {
                    BookshelfScreenExtended(
                        viewModel = viewModel,
                        innerPadding = innerPadding
                    )
                }
            }
            composable(route = BookshelfScreenEnum.BookDetails.name) {
                BookDetailsScreen(
                    book = viewModel.selectedBook,
                    modifier = modifier.padding(innerPadding)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfAppBar(
    currentScreen: BookshelfScreenEnum,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            if (currentScreen == BookshelfScreenEnum.Start) {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = buildAnnotatedString {
                            append("Your ")
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold)
                            ) {
                                append(stringResource(id = R.string.app_name))
                            }
                        },
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        },
        navigationIcon = {
             if (canNavigateBack) {
                 IconButton(onClick = navigateUp) {
                     Icon(
                         imageVector = Icons.Default.ArrowBack,
                         contentDescription = stringResource(R.string.back)
                     )
                 }
             }
        },
        modifier = modifier
    )
}

@Composable
fun BookshelfScreenExtended(
    viewModel: BookshelfViewModel,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        BookshelfScreen(
            bookshelfUiState = viewModel.bookshelfUiState,
            selectedBook = viewModel.selectedBook,
            q = viewModel.q,
            onClickItem = viewModel::onClickItem,
            onQChange = viewModel::onQChanged,
            getBooks = viewModel::getBooks,
            modifier = modifier
                .padding(innerPadding)
                .weight(1f)
        )
        BookDetailsScreen(
            book = viewModel.selectedBook,
            modifier = modifier
                .padding(innerPadding)
                .weight(2f)
        )
    }
}
