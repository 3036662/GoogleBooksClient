package ru.tusur.googlebooksclient.auth

import java.sql.Timestamp

data class Tokens(
    val accessToken:String,
    val expirationTime: Long,
    val tokenType:String,
    val scope: String,
    val refreshToken:String
    )

