package ru.tusur.googlebooksclient.auth

//import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface GoogleAuthInterface {
    @FormUrlEncoded
    @POST("/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    suspend fun requestTokenFromCode(
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("client_id") clientId: String,
        @Field("code_verifier") codeVerifier: String,
        @Field("grant_type") grantType: String,
    ): TokenResponse

    @FormUrlEncoded
    @POST("/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    suspend fun updateToken(
        @Field("client_id") clientId: String,
        @Field("refresh_token") refreshToken:String,
        @Field("grant_type") grantType: String,
    ):UpdatedTokenResponse

    @FormUrlEncoded
    @POST("/revoke")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    suspend fun revokeToken(
        @Field("token") token:String
    )
}