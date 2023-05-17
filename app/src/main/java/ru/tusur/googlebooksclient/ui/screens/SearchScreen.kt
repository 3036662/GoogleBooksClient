package ru.tusur.googlebooksclient.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tusur.googlebooksclient.R
import ru.tusur.googlebooksclient.model.BooksUiState

@Composable
fun SearchScreen(modifier: Modifier=Modifier,booksUiState: BooksUiState,onValueChange: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize(),
       // verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            TextField(value = text,
                onValueChange = { text = it },
                modifier = modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f, true),
                    //.fillMaxWidth(),
                label = { Text(stringResource(R.string.search_for_book_placeholder)) }
            )
            Button(onClick = { onValueChange(text) },modifier= Modifier
                .wrapContentSize()
                .padding(horizontal = 10.dp)
                .weight(0.7f, true)) {
                Text(stringResource(R.string.search_button_text))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        BooksList(books = booksUiState.books )
    }
}