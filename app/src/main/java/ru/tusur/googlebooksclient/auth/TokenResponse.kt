package ru.tusur.googlebooksclient.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TokenResponse(
    @SerialName(value="access_token") val accessToken:String,
    val scope: String,
    @SerialName(value = "token_type") val tokenType:String,
    @SerialName(value = "expires_in")  val expiresIn:Int,
    @SerialName(value = "refresh_token") val refreshToken:String
)

@Serializable
data class UpdatedTokenResponse(
    @SerialName(value="access_token") val accessToken:String,
    @SerialName(value = "expires_in")  val expiresIn:Int,
    val scope: String,
    @SerialName(value = "token_type") val tokenType:String,
)