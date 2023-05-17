package ru.tusur.googlebooksclient.ui





import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tusur.googlebooksclient.BooksApplication
import ru.tusur.googlebooksclient.auth.GoogleAuth
import ru.tusur.googlebooksclient.model.BooksRepository
import ru.tusur.googlebooksclient.model.BooksUiState
import java.io.IOException


class BooksViewModel(private val application: Application,private val booksRepository: BooksRepository):AndroidViewModel(application) {
    private val auth:GoogleAuth= GoogleAuth(
        getApplication<Application>().getSharedPreferences("googlebooksclpref", androidx.activity.ComponentActivity.MODE_PRIVATE)
    );
   private var _uiState= MutableStateFlow(AuthUiState(isAuthorised = auth.isAuthorized))
   val uiAuthState: StateFlow<AuthUiState> = _uiState.asStateFlow()

   private var _booksUiState= MutableStateFlow(BooksUiState(true, listOf()))
    val booksUiState:StateFlow<BooksUiState> = _booksUiState.asStateFlow()

   fun starAuth(context:Context){
              auth.authorization(context)
   }

   fun onAuthError() {
        _uiState.update{it.copy(isAuthorised=false,authorizationError=true)}
   }

   fun authCodeAccepted(queryParameter: String?) {
            _uiState.update{it.copy(isAuthorised = false, authorizationError = (queryParameter==null) )}
       viewModelScope.launch (Dispatchers.IO){
            if (queryParameter != null) {
                auth.getTokenWithCode(queryParameter)
                _uiState.update { it.copy(isAuthorised = auth.isAuthorized) }
            }
        }
   }

   suspend fun getAccessToken():String?{
         return auth.getAccessToken()
   }

    fun logOut() {
        _uiState.update { it.copy(isAuthorised = false) }
        viewModelScope.launch (Dispatchers.IO){
            auth.logout()
        }
    }

    //Books

    fun searchBooks(string: String) {
        viewModelScope.launch (Dispatchers.IO)  {
            try {
                _booksUiState.update {
                    it.copy(
                        statusOK=true,
                        books = booksRepository.searchBooks(string)
                    )
                }
            }catch (e: Exception){
                _booksUiState.update {
                    it.copy(
                        statusOK = false,
                        books= listOf()
                    )

                }
                Log.d("BooksLog",e.toString())
            }
        }
    }


    init{
        Log.d("BooksLog","ViewModel consructor")
   }


    // Create ViewModel with dependencies
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer{
                val application = (this[APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.container.booksRepository
                BooksViewModel(application=application,booksRepository=booksRepository)
            }
        }
    }
}