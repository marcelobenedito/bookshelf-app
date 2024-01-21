package com.github.marcelobenedito.bookshelf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.marcelobenedito.bookshelf.R
import com.github.marcelobenedito.bookshelf.network.AccessInfo
import com.github.marcelobenedito.bookshelf.network.Book
import com.github.marcelobenedito.bookshelf.network.ImageLinks
import com.github.marcelobenedito.bookshelf.network.VolumeInfo
import com.github.marcelobenedito.bookshelf.ui.theme.BookshelfTheme

@Composable
fun BookshelfScreen(
    bookshelfUiState: BookshelfUiState,
    onClickItem: (Book) -> Unit,
    onQChange: (String) -> Unit,
    getBooks: () -> Unit,
    modifier: Modifier = Modifier,
    selectedBook: Book? = null,
    q: String = "",
) {
    when (bookshelfUiState) {
        BookshelfUiState.Loading -> LoadingScreen(modifier = modifier)
        BookshelfUiState.Failure -> FailureScreen(
            retry = getBooks,
            modifier = modifier
        )
        is BookshelfUiState.Success -> BookList(
            books = bookshelfUiState.books,
            selectedBook = selectedBook,
            q = q,
            onClickItem = onClickItem,
            onSearchClicked = getBooks,
            onQChange = onQChange,
            modifier = modifier
        )
    }
}

@Composable
fun BookListItem(
    book: Book,
    onClickItem: (Book) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Color.LightGray.copy(alpha = 0.2f) else MaterialTheme.colorScheme.onPrimary)
            .clickable { onClickItem(book) }
            .padding(16.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.volumeInfo.imageLinks?.thumbnail?.replace("http", "https"))
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.ic_broken_image)
                .crossfade(true)
                .build(),
            contentDescription = book.volumeInfo.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .size(100.dp, 150.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row {
                Column {
                    Text(
                        text = book.volumeInfo.title ?: "Untitled",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = book.volumeInfo.authors?.joinToString(", ") ?: "Unknown",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            book.volumeInfo.categories?.getOrNull(0)?.let {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color(0xFFf1f1f1))
                        .padding(vertical = 4.dp, horizontal = 12.dp)
                ){
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF090809)
                    )
                }
            }
        }
    }
}

@Composable
fun BookList(
    books: List<Book>,
    selectedBook: Book?,
    q: String,
    onClickItem: (Book) -> Unit,
    onSearchClicked: () -> Unit,
    onQChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val searchText = remember { mutableStateOf(q) }
    Column(modifier = modifier.padding(horizontal = 24.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFFf1f1f1))
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.Gray
            )
            BasicTextField(
                value = searchText.value,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = { onSearchClicked() }),
                onValueChange = {
                    searchText.value = it
                    onQChange(it)
                },
                decorationBox = { innerTextField ->
                    if (searchText.value.isEmpty()) {
                        Text(
                            text = "Search",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(books) { book ->
                BookListItem(
                    book = book,
                    isSelected = selectedBook == book,
                    onClickItem = onClickItem
                )
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painterResource(id = R.drawable.loading_img),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
        Text(
            text = stringResource(R.string.loading_books),
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun FailureScreen(retry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.loading_data_failed),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = retry) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookListItemPreview() {
    BookshelfTheme {
        Surface {
            val volumeInfo = VolumeInfo("Title", "", listOf("Author 1"), "", "", 0, "", listOf("Category 1"), ImageLinks(""), "")
            val mockData = Book("", volumeInfo, AccessInfo(""))
            BookListItem(book = mockData, isSelected = false, onClickItem = {})
        }
    }
}

@Preview
@Composable
fun BookListPreview() {
    BookshelfTheme {
        Surface {
            val mockData =
                List(10) {
                    val volumeInfo = VolumeInfo("Title $it", "", listOf("Author $it"), "", "", 0, "", listOf("Category $it"), ImageLinks(""), "")
                    Book("", volumeInfo, AccessInfo(""))
                }
            BookList(
                books = mockData,
                selectedBook = mockData[0],
                q = "",
                onClickItem = {},
                onSearchClicked = {},
                onQChange = {}
            )
        }
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    MaterialTheme {
        Surface {
            LoadingScreen()
        }
    }
}

@Preview
@Composable
fun FailureScreenPreview() {
    MaterialTheme {
        Surface {
            FailureScreen(retry = {})
        }
    }
}