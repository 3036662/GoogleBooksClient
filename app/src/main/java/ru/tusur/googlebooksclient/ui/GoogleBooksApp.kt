package ru.tusur.googlebooksclient.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.tusur.googlebooksclient.R
import ru.tusur.googlebooksclient.ui.screens.SearchScreen
import ru.tusur.googlebooksclient.ui.screens.SignInScreen

// Main Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleBooksAppScreen(modifier: Modifier=Modifier,
                         booksViewModel: BooksViewModel

    ) {
    val uiAuthorisationState  by booksViewModel.uiAuthState.collectAsState()
    val booksUiState by booksViewModel.booksUiState.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {Text(stringResource(R.string.appNameScaffold))},
                colors = centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor =MaterialTheme.colorScheme.onPrimary
                ),
                actions= {
                   if (uiAuthorisationState.isAuthorised){
                       IconButton(onClick = { booksViewModel.logOut() }) {
                           Icon(
                               painter = painterResource(id = R.drawable.logout_48px),
                               stringResource(R.string.logout_button),
                               tint = Color.White
                           )
                       }
                   }
                }
            )

            }
        ){
        Surface(
            modifier= Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ){


            if (!uiAuthorisationState.isAuthorised) { // not authorised user
                SignInScreen(booksViewModel,uiAuthorisationState)
            }
            else{ // authorised user
                    SearchScreen(booksUiState = booksUiState){ str->booksViewModel.searchBooks(str)}
            }
        }
    }


}