package ru.tusur.googlebooksclient

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import ru.tusur.googlebooksclient.auth.MY_REDIRECT_URI
import ru.tusur.googlebooksclient.ui.BooksViewModel
import ru.tusur.googlebooksclient.ui.GoogleBooksAppScreen
import ru.tusur.googlebooksclient.ui.theme.GoogleBooksClientTheme


class MainActivity : ComponentActivity() {
    //private val viewModel:BooksViewModel by  viewModels()
    private lateinit var viewModel: BooksViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (application as BooksApplication).container
        viewModel = BooksViewModel(application,appContainer.booksRepository)

        handleIntent(intent)
        setContent {
            GoogleBooksClientTheme {
                GoogleBooksAppScreen(booksViewModel = viewModel)
            }
        }
    }

    // this activity is launched from google link in browser
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        //val appLinkAction = intent.action
        val appLinkData: Uri? = intent.data

        if (appLinkData?.scheme==MY_REDIRECT_URI){
           // Log.d("BooksLog",appLinkData.toString())
            when {
                appLinkData.queryParameterNames.contains("code")->{
                    viewModel.authCodeAccepted(appLinkData.getQueryParameter("code") )
                }
                appLinkData.queryParameterNames.contains("error")->{
                    viewModel.onAuthError()
                }

            }
        }
    }

}

