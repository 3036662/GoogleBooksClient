package ru.tusur.googlebooksclient.ui

data class AuthUiState(
    val isAuthorised:Boolean=false,
    val authorizationError:Boolean=false,
)
