package ru.tusur.googlebooksclient.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.tusur.googlebooksclient.model.Book
import ru.tusur.googlebooksclient.R

@Composable
fun BooksList(modifier:Modifier=Modifier, books:List<Book>){
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp),
    ){
        items(items = books) {
                book -> BookCard(book)
        }
    }

}

@Composable
fun BookCard(book: Book,modifier: Modifier=Modifier){
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
            //.aspectRatio(1f),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
      //  Log.d("BooksLog","Now we have"+book.thumbnail)

        Row {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.img?:book.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = book.title,
                modifier = Modifier.height(150.dp).width(150.dp),
                error = painterResource(id = R.drawable.baseline_broken_image_24),
                placeholder = painterResource(R.drawable.baseline_downloading_24),
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = modifier.width(10.dp))
            Column {
                Text(text = book.title)
                Text(text = book.date?:"")
            }

        }
        //
    }
}