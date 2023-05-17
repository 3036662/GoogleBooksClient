package ru.tusur.googlebooksclient.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tusur.googlebooksclient.R
import ru.tusur.googlebooksclient.ui.AuthUiState
import ru.tusur.googlebooksclient.ui.BooksViewModel


@Composable
fun SignInScreen(authViewModel:BooksViewModel, uiState: AuthUiState){
        val context= LocalContext.current
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Row {
                Button(
                    onClick = { authViewModel.starAuth(context) },
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(text = stringResource(R.string.sign_in_with_google))
                }
            }

            if (uiState.authorizationError){
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                  Text(text = stringResource(R.string.authorization_error))
                }

            }
        }

}