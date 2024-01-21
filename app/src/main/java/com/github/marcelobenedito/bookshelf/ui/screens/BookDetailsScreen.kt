package com.github.marcelobenedito.bookshelf.ui.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.marcelobenedito.bookshelf.R
import com.github.marcelobenedito.bookshelf.network.AccessInfo
import com.github.marcelobenedito.bookshelf.network.Book
import com.github.marcelobenedito.bookshelf.network.ImageLinks
import com.github.marcelobenedito.bookshelf.network.VolumeInfo

@Composable
fun BookDetailsScreen(book: Book?, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(false) }

    Box(modifier = modifier
        .background(Color.Transparent)
        .fillMaxSize()
    ) {
        if (book == null) {
            Text(text = "Please, select a book...")
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 64.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(book.volumeInfo.imageLinks?.thumbnail?.replace("http", "https"))
                            .error(R.drawable.ic_broken_image)
                            .placeholder(R.drawable.loading_img)
                            .crossfade(true)
                            .build(),
                        contentDescription = book.volumeInfo.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.width(200.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.padding(16.dp)) {
                    Column {
                        Text(
                            text = book.volumeInfo.title ?: "Untitled",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = book.volumeInfo.authors?.joinToString(", ") ?: "Unknown",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .width(120.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        MaterialTheme.colorScheme.primaryContainer.copy(
                                            alpha = 0.3f
                                        )
                                    )
                                    .padding(10.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.book),
                                    contentDescription = null,
                                    Modifier.height(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = stringResource(
                                        R.string.page_count,
                                        book.volumeInfo.pageCount ?: 0
                                    ),
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .width(120.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        MaterialTheme.colorScheme.primaryContainer.copy(
                                            alpha = 0.3f
                                        )
                                    )
                                    .padding(10.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.language),
                                    contentDescription = null,
                                    Modifier.width(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = book.volumeInfo.language ?: "Unknown",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = stringResource(R.string.description),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = book.volumeInfo.description ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFa4a4a2)
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Row {
                    Button(
                        onClick = {
                            val intent =
                                Intent(Intent.ACTION_VIEW, Uri.parse(book.accessInfo.webReaderLink))
                            startActivity(context, intent, null)
                        },
                        modifier = Modifier
                            .height(48.dp)
                            .weight(8f)
                    ) {
                        Text(text = stringResource(R.string.read))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedIconButton(
                        onClick = { isFavorite = !isFavorite },
                        border = BorderStroke(
                            1.dp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        colors = IconButtonDefaults.outlinedIconButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun BookDetailsScreenPreview() {
    MaterialTheme {
        Surface {
            val description = "Lorem ipsum dolor sit amet. Et nobis accusantium et ipsa perferendis rem nulla debitis sed consectetur voluptatem et dignissimos totam qui commodi doloremque qui quidem sint."
            val volumeInfo = VolumeInfo("Title", description, listOf("Author 1", "Author 2"), "", "", 435, "", listOf("Category 1"), ImageLinks(""), "English")
            val mockData = Book("", volumeInfo, AccessInfo(""))
            BookDetailsScreen(book = mockData)
        }
    }
}
